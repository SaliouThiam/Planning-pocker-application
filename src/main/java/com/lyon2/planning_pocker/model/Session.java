package com.lyon2.planning_pocker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Représente une session de Planning Poker.
 * <p>
 * Une session regroupe plusieurs joueurs autour d'un titre et d'une description,
 * et se déroule sur une période définie entre une date de début et une date de fin.
 * Elle utilise un mode de calcul {@link Mode} pour déterminer l’estimation finale.
 * Les méthodes d’accès (getters, setters), ainsi que les constructeurs, sont
 * générés automatiquement grâce à Lombok, sauf le constructeur spécifique défini
 * manuellement.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Session {

    /**
     * Identifiant unique de la session.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int session_id;

    /**
     * Titre de la session de Planning Poker.
     */
    private String titre;

    /**
     * code de la session de Planning Poker.
     */
    private String sessionCode; // ex: ABC123


    /**
     * Description résumant le contexte ou l’objectif de la session.
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
     * Mode de calcul utilisé pour agréger les estimations.
     * Par défaut, le mode appliqué est {@link Mode#STRICT}.
     */
    @Enumerated(EnumType.STRING)
    private Mode mode = Mode.STRICT;

    /**
     * Liste des joueurs participant à la session.
    */
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Joueur> joueur = new ArrayList<>() ;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Fonctionnalite> fonctionnalite = new ArrayList<>();

    /**
     * Booleen pour vérifier est ce que la session est en pause ou pas.
     */
    private boolean paused= false;


    /**
     * Constructeur simplifié permettant de créer une session sans préciser
     * explicitement le mode ni la liste des joueurs.
     * <p>
     * Le mode est automatiquement initialisé à {@link Mode#STRICT}.
     *
     * @param titre      Titre de la session.
     * @param description Description de la session.
     */
    public Session(String titre, String description, LocalDateTime debut, LocalDateTime fin ,  List<Fonctionnalite> fonctionnalite, List<Joueur> joueur ) {
        this.fonctionnalite = fonctionnalite;
        this.titre = titre;
        this.description = description;
        this.joueur = joueur;
        this.debut=debut;
        this.fin=fin;

    }
}
