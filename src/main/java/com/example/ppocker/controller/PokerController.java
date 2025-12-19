package com.example.ppocker.controller;

import com.example.ppocker.model.Vote;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Contrôleur WebSocket pour gérer les votes des joueurs lors d'une session de Planning Poker.
 * <p>
 * Les votes sont reçus via une destination WebSocket et renvoyés à tous les clients abonnés.
 * </p>
 */
@Controller
public class PokerController {

    /**
     * Reçoit un vote envoyé par un client et le renvoie à tous les clients abonnés.
     *
     * @param vote Le vote envoyé par un joueur, contenant le pseudo et la valeur du vote.
     * @return Le même vote, diffusé à tous les clients abonnés sur "/topic/votes".
     */
    @MessageMapping("/vote")   // Message envoyé depuis le client
    @SendTo("/topic/votes")    // Tous les clients reçoivent la mise à jour
    public Vote sendVote(Vote vote) {
        System.out.println("Vote reçu : " + vote.getPlayer() + " → " + vote.getValue());
        return vote;
    }
}
