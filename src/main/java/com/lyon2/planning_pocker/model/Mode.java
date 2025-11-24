package com.lyon2.planning_pocker.model;

/**
 * Représente les différents modes de calcul utilisés dans une session
 * de Planning Poker pour déterminer la valeur finale après les votes.
 * <p>
 * Chaque mode correspond à une règle de décision différente :
 * <ul>
 *     <li>{@link #STRICT} : Toutes les estimations doivent être identiques.</li>
 *     <li>{@link #Moyenne} : La valeur retenue est la moyenne des estimations.</li>
 *     <li>{@link #Median} : La valeur retenue est la médiane des estimations.</li>
 *     <li>{@link #Majorite_Absolue} : La valeur retenue est celle obtenant plus de 50% des votes.</li>
 *     <li>{@link #Majorite_relave} : La valeur retenue est celle ayant la plus grande fréquence, sans seuil.</li>
 * </ul>
 */

public enum Mode {

    /**
     * Toutes les estimations doivent être identiques pour valider le résultat.
     */
    STRICT,

    /**
     * La valeur finale correspond à la moyenne arithmétique des votes.
     */
    Moyenne,

    /**
     * La valeur finale correspond à la médiane des votes.
     */
    Median,

    /**
     * La valeur retenue est celle ayant obtenu plus de 50% des voix.
     */
    Majorite_Absolue,

    /**
     * La valeur retenue est celle la plus fréquente, même sans atteindre 50%.
     */
    Majorite_relative
}
