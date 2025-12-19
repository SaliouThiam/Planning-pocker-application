package com.example.ppocker.service;

import com.example.ppocker.model.PlanningMode;
import com.example.ppocker.model.Player;
import com.example.ppocker.model.Session;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service de gestion des sessions de Planning Poker.
 * <p>
 * Permet de créer des sessions, gérer les joueurs, les votes,
 * révéler les résultats et avancer dans le backlog.
 * </p>
 */
@Service
public class SessionService {

    /** Map des sessions actives : id de session -> session. */
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    /** Générateur aléatoire utilisé pour créer le code des sessions. */
    private final Random random = new Random();

    /** Template pour envoyer des messages via WebSocket. */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructeur du service.
     *
     * @param messagingTemplate template pour la communication WebSocket
     */
    public SessionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // =========================
    // SESSION CREATION
    // =========================

    /**
     * Crée une nouvelle session avec un backlog et un mode de calcul des votes.
     *
     * @param name nom de la session
     * @param backlog liste des fonctionnalités à estimer
     * @param mode mode de calcul des votes (STRICT ou AVERAGE)
     * @return la session créée
     */
    public Session createSession(String name, List<String> backlog, PlanningMode mode) {
        String id = generateCode();

        Session s = new Session();
        s.setId(id);
        s.setName(name);
        s.setBacklog(backlog != null ? new ArrayList<>(backlog) : new ArrayList<>());
        s.setMode(mode != null ? mode : PlanningMode.STRICT);

        sessions.put(id, s);
        return s;
    }

    /**
     * Récupère une session existante par son identifiant.
     *
     * @param id identifiant de la session
     * @return Optional contenant la session si trouvée
     */
    public Optional<Session> getSession(String id) {
        return Optional.ofNullable(sessions.get(id));
    }

    // =========================
    // JOIN & VOTE
    // =========================

    /**
     * Permet à un joueur de rejoindre une session.
     *
     * @param id identifiant de la session
     * @param pseudo pseudo du joueur
     * @return true si le joueur a été ajouté, false si la session n'existe pas
     */
    public boolean joinSession(String id, String pseudo) {
        Session s = sessions.get(id);
        if (s == null) return false;

        Player p = new Player();
        p.setPseudo(pseudo);
        s.getPlayers().add(p);

        s.getVotes().put(pseudo, 0);
        return true;
    }

    /**
     * Définit le vote d'un joueur pour une session.
     *
     * @param sessionId identifiant de la session
     * @param pseudo pseudo du joueur
     * @param vote valeur du vote
     */
    public void setVote(String sessionId, String pseudo, int vote) {
        Session s = sessions.get(sessionId);
        if (s == null) return;
        s.getVotes().put(pseudo, vote);
    }

    // =========================
    // REVEAL (LOGIQUE DES MODES)
    // =========================

    /**
     * Révèle les votes d'une session selon le mode de calcul (STRICT ou AVERAGE)
     * et envoie le résultat via WebSocket.
     *
     * @param sessionId identifiant de la session
     */
    public void reveal(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s == null) return;

        s.setRevealed(true);

        Map<String, Integer> votes = s.getVotes();
        PlanningMode mode = s.getMode();

        Integer result = null;

        if (mode == PlanningMode.STRICT) {
            result = computeStrict(votes);
        } else if (mode == PlanningMode.AVERAGE) {
            result = computeAverage(votes);
        }

        votes.put("RESULT", result);

        messagingTemplate.convertAndSend(
                "/topic/session/" + sessionId + "/reveal",
                votes
        );
    }

    // =========================
    // NEXT FEATURE
    // =========================

    /**
     * Passe à la fonctionnalité suivante dans le backlog et réinitialise les votes.
     *
     * @param sessionId identifiant de la session
     */
    public void nextFeature(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s == null) return;

        s.setRevealed(false);
        s.setCurrentIndex(Math.min(
                s.getCurrentIndex() + 1,
                s.getBacklog().size()
        ));

        s.getVotes().replaceAll((k, v) -> 0);
        s.getVotes().remove("RESULT");
    }

    // =========================
    // MODE CALCULS
    // =========================

    /**
     * Calcule le résultat strict : toutes les votes doivent être identiques.
     *
     * @param votes map des votes
     * @return la valeur unique si tous les votes sont identiques, sinon null
     */
    private Integer computeStrict(Map<String, Integer> votes) {
        Set<Integer> uniques = new HashSet<>();

        for (Integer v : votes.values()) {
            if (v == null || v <= 0) return null;
            uniques.add(v);
        }

        return uniques.size() == 1 ? uniques.iterator().next() : null;
    }

    /**
     * Calcule la moyenne des votes positifs.
     *
     * @param votes map des votes
     * @return valeur moyenne arrondie, ou null si aucun vote positif
     */
    private Integer computeAverage(Map<String, Integer> votes) {
        int sum = 0;
        int count = 0;

        for (Integer v : votes.values()) {
            if (v != null && v > 0) {
                sum += v;
                count++;
            }
        }

        if (count == 0) return null;
        return Math.round((float) sum / count);
    }

    // =========================
    // RESTORE SESSION
    // =========================

    /**
     * Restaure une session importée depuis un fichier JSON et envoie l'état via WebSocket.
     *
     * @param imported session importée
     * @return la session restaurée
     */
    public Session restoreSession(Session imported) {
        if (imported.getVotes() == null)
            imported.setVotes(new ConcurrentHashMap<>());

        if (imported.getPlayers() == null)
            imported.setPlayers(Collections.synchronizedList(new ArrayList<>()));

        if (imported.getBacklog() == null)
            imported.setBacklog(new ArrayList<>());

        if (imported.getMode() == null)
            imported.setMode(PlanningMode.STRICT);

        sessions.put(imported.getId(), imported);

        Map<String, Object> payload = new HashMap<>();
        payload.put("currentIndex", imported.getCurrentIndex());
        payload.put("votes", imported.getVotes());
        payload.put(
                "feature",
                imported.getBacklog().size() > imported.getCurrentIndex()
                        ? imported.getBacklog().get(imported.getCurrentIndex())
                        : null
        );

        messagingTemplate.convertAndSend(
                "/topic/session/" + imported.getId() + "/feature",
                Optional.of(payload)
        );

        return imported;
    }

    // =========================
    // UTILS
    // =========================

    /**
     * Génère un code alphanumérique unique pour identifier une session.
     *
     * @return code de session à 5 caractères
     */
    private String generateCode() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }
}
