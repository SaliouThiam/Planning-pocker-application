package com.lyon2.planning_pocker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un joueur participant à une {@link Session} de Planning Poker.
 *
 * <p>Cette entité contient les informations essentielles relatives à un joueur
 * ainsi que ses relations avec une session et ses votes.</p>
 *
 * <h3>Relations :</h3>
 * <ul>
 *     <li><b>ManyToOne → Session</b> : un joueur appartient à une unique session.</li>
 *     <li><b>OneToMany → Vote</b> : un joueur peut effectuer plusieurs votes
 *     au cours de la session.</li>
 * </ul>
 *
 * <p>Les éléments boilerplate tels que les getters, setters, constructeurs et
 * builder sont automatiquement générés grâce à Lombok.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Joueur {

    /**
     * Identifiant unique du joueur.
     * <p>
     * Il est généré automatiquement via la stratégie {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_joueur;

    /**
     * Pseudonyme sous lequel le joueur sera affiché dans une session.
     */
    private String pseudo;

    /**
     * Session à laquelle le joueur participe.
     *
     * <p>Relation : plusieurs joueurs peuvent appartenir à une même session.</p>
     */
    @ManyToOne
    private Session session;

    /**
     * Liste des votes effectués par le joueur au cours de la session.
     *
     * <p>Relation : un joueur peut enregistrer plusieurs votes,
     * mais chaque vote appartient à un seul joueur.</p>
     *
     * <p>La suppression d’un joueur entraîne automatiquement
     * la suppression de tous ses votes associées (cascade ALL).</p>
     */
    @OneToMany(mappedBy = "joueur", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();
}
