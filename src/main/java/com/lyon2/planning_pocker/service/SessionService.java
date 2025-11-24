package com.lyon2.planning_pocker.service;

import com.lyon2.planning_pocker.dto.SessionRequest;
import com.lyon2.planning_pocker.model.Fonctionnalite;
import com.lyon2.planning_pocker.model.Joueur;
import com.lyon2.planning_pocker.model.Session;
import com.lyon2.planning_pocker.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Spring chargé de la gestion des {@link Session} de Planning Poker.
 *
 * <p>Ce service encapsule la logique métier liée à la création de sessions,
 * l'ajout de joueurs et de fonctionnalités, et la génération automatique
 * d'un code de session unique.</p>
 *
 * <p>Il utilise {@link SessionRepository} pour persister les sessions
 * dans la base de données et bénéficier des fonctionnalités de JPA.</p>
 */
@Service
public class SessionService {

    /**
     * Repository pour accéder aux sessions.
     */
    private final SessionRepository sessionRepository;

    /**
     * Constructeur du service, injecte le {@link SessionRepository}.
     *
     * @param sessionRepository repository pour persister les sessions
     */
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Crée et persiste une nouvelle session de Planning Poker à partir
     * des informations fournies dans un {@link SessionRequest}.
     *
     * <p>Cette méthode :</p>
     * <ul>
     *     <li>Crée la session avec son titre, description, dates et mode</li>
     *     <li>Génère un code de session unique</li>
     *     <li>Ajoute les joueurs fournis dans le request (avec référence à la session)</li>
     *     <li>Ajoute les fonctionnalités fournies dans le request (avec référence à la session)</li>
     *     <li>Persiste la session complète avec {@link SessionRepository#save(Object)}</li>
     * </ul>
     *
     * @param request objet DTO contenant les informations pour créer la session
     * @return la session créée et persistée
     */
    public Session createSession(SessionRequest request) {
        Session session = Session.builder()
                .titre(request.getTitre())
                .description(request.getDescription())
                .debut(request.getDebut())
                .fin(request.getFin())
                .mode(request.getMode())
                .sessionCode(generateSessionCode())
                .build();

        // Ajouter les joueurs
        if (request.getJoueurs() != null) {
            List<Joueur> joueurs = request.getJoueurs().stream()
                    .map(pseudo -> Joueur.builder()
                            .pseudo(pseudo)
                            .session(session)
                            .build())
                    .toList();
            session.setJoueur(joueurs);
        }

        // Ajouter les fonctionnalités
        if (request.getFonctionnalites() != null) {
            List<Fonctionnalite> fonctionnalites = request.getFonctionnalites().stream()
                    .map(f -> Fonctionnalite.builder()
                            .titre(f)
                            .session(session)
                            .build())
                    .toList();
            session.setFonctionnalite(fonctionnalites);
        }

        return sessionRepository.save(session);
    }

    /**
     * Génère un code de session aléatoire, utilisable comme identifiant simple
     * pour permettre aux joueurs de rejoindre la session.
     *
     * @return un code de session unique de 6 caractères
     */
    private String generateSessionCode() {
        return java.util.UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
