package com.lyon2.planning_pocker.model;

/**
 * Représente les différentes valeurs de cartes utilisables dans une session
 * de Planning Poker.
 *
 * <p>Ces valeurs correspondent généralement à une suite de Fibonacci
 * simplifiée, utilisée pour estimer la complexité ou l'effort des
 * fonctionnalités lors d'une session Agile.</p>
 *
 * <h3>Liste des valeurs :</h3>
 * <ul>
 *     <li>{@link #ZERO} : Valeur 0</li>
 *     <li>{@link #ONE} : Valeur 1</li>
 *     <li>{@link #TWO} : Valeur 2</li>
 *     <li>{@link #THREE} : Valeur 3</li>
 *     <li>{@link #FIVE} : Valeur 5</li>
 *     <li>{@link #EIGHT} : Valeur 8</li>
 *     <li>{@link #THIRTEEN} : Valeur 13</li>
 *     <li>{@link #CAFEE} : Carte spéciale souvent utilisée pour signifier
 *     une pause ou une impossibilité d’estimer.</li>
 * </ul>
 *
 * <p>Ces valeurs sont typiquement utilisées lors de chaque vote pour exprimer
 * l'estimation du joueur.</p>
 */
public enum ValeurCarte {
    ZERO,
    ONEHALF,
    ONE,
    TWO,
    THREE,
    FIVE,
    EIGHT,
    THIRTEEN,

    /**
     * Carte spéciale représentant une "pause café" ou
     * l'incapacité du joueur à donner une estimation.
     */
    CAFEE
}
