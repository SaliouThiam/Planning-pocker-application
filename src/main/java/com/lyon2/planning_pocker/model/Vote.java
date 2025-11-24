package com.lyon2.planning_pocker.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représente un vote effectué par un joueur sur une fonctionnalité
 * au sein d'une session de Planning Poker.
 *
 * <p>Chaque vote est associé à :</p>
 * <ul>
 *     <li>un {@link Joueur} qui réalise le vote ;</li>
 *     <li>une {@link Fonctionnalite} concernée par ce vote ;</li>
 *     <li>une valeur de carte {@link ValeurCarte} choisie par le joueur.</li>
 * </ul>
 *
 * <p>L'entité est persistée en base de données, et ses méthodes
 * (constructeurs, getters, setters, builder) sont générées par Lombok.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vote {

    /**
     * Identifiant unique du vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vote;

    /**
     * Joueur ayant émis ce vote.
     * <p>
     * Plusieurs votes peuvent appartenir au même joueur, mais pour
     * une fonctionnalité donnée, un seul vote doit généralement être présent.
     * </p>
     */
    @ManyToOne
    private Joueur joueur;

    /**
     * Fonctionnalité sur laquelle porte ce vote.
     * <p>
     * Une fonctionnalité peut recevoir plusieurs votes, correspondant
     * aux estimations des différents joueurs.
     * </p>
     */
    @ManyToOne
    private Fonctionnalite fonctionnalite;

    /**
     * Valeur choisie par le joueur pour ce vote.
     * <p>
     * La valeur correspond à l'une des cartes de la suite de Planning Poker.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private ValeurCarte value;

}
