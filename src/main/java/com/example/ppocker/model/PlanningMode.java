package com.example.ppocker.model;

/**
 * Enumération des modes de planification pour le Planning Poker.
 * <p>
 * Ces modes définissent la logique utilisée pour calculer l'estimation finale
 * d'une fonctionnalité à partir des votes des participants.
 * </p>
 */
public enum PlanningMode {

    /**
     * Mode strict : tous les votes doivent être égaux pour obtenir un résultat.
     * Sinon, le résultat final est considéré comme non valide (désaccord).
     */
    STRICT,

    /**
     * Mode average (moyenne) : le résultat final est calculé comme la moyenne
     * arrondie des votes positifs.
     */
    AVERAGE
}
