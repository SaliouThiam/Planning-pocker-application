package com.lyon2.planning_pocker.dto;

import com.lyon2.planning_pocker.model.Mode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) utilisé pour créer une nouvelle {@link com.lyon2.planning_pocker.model.Session}
 * via l'API REST.
 *
 * <p>Ce DTO encapsule toutes les informations nécessaires pour initialiser
 * une session, y compris le titre, la description, les dates, le mode de vote,
 * ainsi que la liste des joueurs et des fonctionnalités.</p>
 *
 * <p>Ce DTO est utilisé comme paramètre dans le contrôleur
 * {@link com.lyon2.planning_pocker.controller.SessionController}.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionRequest {

    /**
     * Titre de la session de Planning Poker.
     */
    private String titre;

    /**
     * Description détaillant le contexte ou l'objectif de la session.
     */
    private String description;

    /**
     * Date et heure de début de la session.
     */
    private LocalDateTime debut;

    /**
     * Date et heure de fin de la session.
     */
    private LocalDateTime fin;

    /**
     * Mode de calcul utilisé pour agréger les votes.
     * Correspond à l'un des {@link Mode} définis.
     */
    private Mode mode;

    /**
     * Liste des pseudos des joueurs participant à la session.
     * <p>Chaque pseudo sera transformé en {@link com.lyon2.planning_pocker.model.Joueur}
     * lors de la création de la session.</p>
     */
    private List<String> joueurs;

    /**
     * Liste des fonctionnalités à ajouter au backlog de la session.
     * <p>Chaque élément sera transformé en {@link com.lyon2.planning_pocker.model.Fonctionnalite}
     * lors de la création de la session.</p>
     */
    private List<String> fonctionnalites;
}
