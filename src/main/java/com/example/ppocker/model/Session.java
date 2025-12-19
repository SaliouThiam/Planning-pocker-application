package com.example.ppocker.model;

import lombok.Data;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Représente une session de Planning Poker.
 * <p>
 * Une session contient un backlog de fonctionnalités à estimer,
 * une liste de joueurs, l'état des votes et le mode de calcul des votes.
 * </p>
 */
@Data
public class Session {

    /** Code unique de la session (ex: "AB12F"). */
    private String id;

    /** Nom de la session. */
    private String name;

    /** Liste des fonctionnalités à estimer (backlog). */
    private List<String> backlog = new ArrayList<>();

    /** Index de la fonctionnalité courante dans le backlog. */
    private int currentIndex = 0;

    /**
     * Map des votes pour la session.
     * <p>
     * Clé : pseudo du joueur.<br>
     * Valeur : vote du joueur (entier) ou "?" si pas encore voté.
     * </p>
     */
    private Map<String, Integer> votes = new ConcurrentHashMap<>();

    /** Liste des joueurs présents dans la session. */
    private List<Player> players = Collections.synchronizedList(new ArrayList<>());

    /** Indique si les votes ont été révélés ou non. */
    private boolean revealed = false;

    /** Mode de calcul des votes pour la session (STRICT ou AVERAGE). */
    private PlanningMode mode = PlanningMode.STRICT;

}
