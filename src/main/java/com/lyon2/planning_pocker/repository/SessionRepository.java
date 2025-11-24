package com.lyon2.planning_pocker.repository;

import com.lyon2.planning_pocker.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité {@link Session}.
 *
 * <p>Ce repository fournit l'ensemble des opérations CRUD de base
 * (création, lecture, mise à jour, suppression) sans qu'il soit nécessaire
 * d’écrire la moindre implémentation.</p>
 *
 * <p>Il hérite de {@link JpaRepository}, ce qui permet notamment :</p>
 * <ul>
 *     <li>d'obtenir toutes les sessions : {@code findAll()}</li>
 *     <li>d'enregistrer une session : {@code save(session)}</li>
 *     <li>de rechercher une session par son id : {@code findById(id)}</li>
 *     <li>de supprimer une session : {@code deleteById(id)}</li>
 *     <li>d'utiliser des méthodes dérivées de nommage ou des requêtes personnalisées</li>
 * </ul>
 *
 * <p>Des méthodes supplémentaires peuvent être ajoutées en déclarant
 * simplement leur signature. Spring Data JPA générera automatiquement
 * leur implémentation si elles respectent les conventions.</p>
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
