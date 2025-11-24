package com.lyon2.planning_pocker.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une fonctionnalité (User Story) d'un backlog
 * dans une session de Planning Poker.
 *
 * <p>
 * Chaque fonctionnalité appartient à une {@link Session}
 * et reçoit des {@link Vote} envoyés par les joueurs.
 * Une fois que les règles d'estimation définies pour la session
 * sont remplies (strict, moyenne, médiane, etc.),
 * la fonctionnalité peut être marquée comme validée.
 * </p>
 *
 * <h3>Champs principaux :</h3>
 * <ul>
 *     <li><b>titre</b> : nom court décrivant la fonctionnalité</li>
 *     <li><b>description</b> : détail de la fonctionnalité</li>
 *     <li><b>session</b> : session à laquelle appartient cette fonctionnalité</li>
 *     <li><b>validated</b> : indique si la fonctionnalité a été validée selon les règles du mode de jeu</li>
 *     <li><b>votes</b> : liste des votes associés à cette fonctionnalité</li>
 * </ul>
 *
 * <h3>Relations :</h3>
 * <ul>
 *     <li>{@code @ManyToOne Session} : plusieurs fonctionnalités appartiennent à une seule session</li>
 *     <li>{@code @OneToMany Vote} : une fonctionnalité peut recevoir plusieurs votes</li>
 * </ul>
 *
 * <h3>Persistance :</h3>
 * La classe est une entité JPA persistée dans la base H2.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Fonctionnalite {

    /**
     * Identifiant unique de la fonctionnalité.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre court de la fonctionnalité.
     * Sert d’identifiant lisible par les joueurs.
     */
    private String titre;

    /**
     * Description détaillée de la fonctionnalité.
     * Permet d’apporter du contexte avant le vote.
     */
    private String description;

    /**
     * Session à laquelle cette fonctionnalité appartient.
     * Une session peut contenir plusieurs fonctionnalités.
     */
    @ManyToOne
    private Session session;

    /**
     * Indique si la fonctionnalité a été validée
     * après l'application des règles de vote de la session.
     */
    private boolean validated;

    /**
     * Liste des votes associés à la fonctionnalité.
     * Chaque joueur vote une valeur de carte (1, 2, 3, 5, 8, etc.).
     */
    @OneToMany(mappedBy = "fonctionnalite", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();
}
