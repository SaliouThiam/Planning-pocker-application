package com.example.ppocker.dto;

import lombok.Data;

/**
 * DTO utilisé lorsqu'un joueur souhaite rejoindre une session de Planning Poker.
 * <p>
 * Contient uniquement le pseudo du joueur.
 * </p>
 */
@Data
public class JoinRequest {

    /** Pseudo du joueur rejoignant la session. */
    private String pseudo;
}
