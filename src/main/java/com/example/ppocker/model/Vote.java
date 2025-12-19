package com.example.ppocker.model;

import lombok.Data;

/**
 * Représente le vote d'un joueur dans une session de Planning Poker.
 * <p>
 * Chaque vote contient le pseudo du joueur et la valeur de son estimation.
 * </p>
 */
@Data
public class Vote {

    /** Pseudo du joueur ayant effectué le vote. */
    private String player;

    /** Valeur du vote du joueur. */
    private int value;

    /**
     * Retourne le pseudo du joueur.
     *
     * @return le pseudo du joueur
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Définit le pseudo du joueur.
     *
     * @param player le pseudo du joueur
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     * Retourne la valeur du vote.
     *
     * @return la valeur du vote
     */
    public int getValue() {
        return value;
    }

    /**
     * Définit la valeur du vote.
     *
     * @param value la valeur du vote
     */
    public void setValue(int value) {
        this.value = value;
    }
}
