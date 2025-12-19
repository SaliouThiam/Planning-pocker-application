package com.example.ppocker.model;

import lombok.Data;

/**
 * Représente un joueur dans une session de Planning Poker.
 * <p>
 * Chaque joueur est identifié par son pseudo.
 * </p>
 */
@Data
public class Player {

    /** Pseudo du joueur. */
    private String pseudo;
}
