package com.example.ppocker.controller;

import com.example.ppocker.model.Vote;
import com.example.ppocker.model.Session;
import com.example.ppocker.service.SessionService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur WebSocket pour le Planning Poker.
 * <p>
 * Gère la communication temps réel entre les clients (joueurs et Scrum Master)
 * et le serveur pour les actions de vote, révélation des votes et passage à la
 * fonctionnalité suivante.
 * </p>
 */
@Controller
public class SocketController {

    /** Service gérant la logique métier des sessions. */
    private final SessionService sessionService;

    /** Template pour envoyer des messages aux clients via WebSocket. */
    private final SimpMessagingTemplate template;

    /**
     * Constructeur.
     *
     * @param sessionService Service des sessions injecté par Spring.
     * @param template       Template pour envoyer des messages WebSocket.
     */
    public SocketController(SessionService sessionService, SimpMessagingTemplate template) {
        this.sessionService = sessionService;
        this.template = template;
    }

    /**
     * Reçoit un vote envoyé par un joueur et met à jour la session correspondante.
     * <p>
     * Si la session est révélée, tous les votes sont envoyés aux clients.
     * Sinon, seule la taille des votes (nombre de joueurs ayant voté) est envoyée.
     * </p>
     *
     * @param id   Identifiant de la session.
     * @param vote Objet Vote contenant le pseudo du joueur et sa valeur.
     */
    @MessageMapping("/session/{id}/vote")
    public void vote(@DestinationVariable String id, Vote vote) {
        sessionService.setVote(id, vote.getPlayer(), vote.getValue());
        sessionService.getSession(id).ifPresent(session -> {
            if (session.isRevealed()) {
                template.convertAndSend("/topic/session/" + id + "/votes", session.getVotes());
            } else {
                template.convertAndSend("/topic/session/" + id + "/votes-count", session.getVotes().size());
            }
        });
    }

    /**
     * Action du Scrum Master pour révéler tous les votes d'une session.
     * <p>
     * Les votes sont envoyés à tous les clients abonnés au topic correspondant.
     * </p>
     *
     * @param id Identifiant de la session.
     */
    @MessageMapping("/session/{id}/reveal")
    public void reveal(@DestinationVariable String id) {
        sessionService.reveal(id);
        sessionService.getSession(id).ifPresent(session ->
                template.convertAndSend("/topic/session/" + id + "/reveal", session.getVotes())
        );
    }

    /**
     * Action du Scrum Master pour passer à la fonctionnalité suivante dans une session.
     * <p>
     * Les votes sont réinitialisés et la nouvelle fonctionnalité courante est envoyée
     * à tous les clients abonnés au topic correspondant.
     * </p>
     *
     * @param id Identifiant de la session.
     */
    @MessageMapping("/session/{id}/next")
    public void next(@DestinationVariable String id) {
        sessionService.nextFeature(id);

        sessionService.getSession(id).ifPresent(session -> {
            Map<String, Object> payload = new HashMap<>();
            payload.put("currentIndex", session.getCurrentIndex());

            String feature = "";
            if (session.getBacklog().size() > session.getCurrentIndex()) {
                feature = session.getBacklog().get(session.getCurrentIndex());
            }

            payload.put("feature", feature);
            payload.put("votes", session.getVotes());

            template.convertAndSend("/topic/session/" + id + "/feature", Optional.of(payload));
        });
    }
}
