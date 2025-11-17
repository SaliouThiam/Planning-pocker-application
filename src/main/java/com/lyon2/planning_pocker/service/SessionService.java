package com.lyon2.planning_pocker.service;

import com.lyon2.planning_pocker.model.Mode;
import com.lyon2.planning_pocker.model.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service qui gère la création de sessions.
 * Cette classe contient les méthodes pour créer des sessions avec des validations.
 */
@Service
public class SessionService {

    /**
     * Crée une session avec les informations données.
     * @param session_id L'ID unique de la session.
     * @param titre Le titre de la session.
     * @param description La description de la session.
     * @param debut La date et l'heure de début de la session.
     * @param fin La date et l'heure de fin de la session.
     * @param mode Le mode de la session.
     * @return Une nouvelle instance de {@link Session}.
     * @throws IllegalArgumentException Si la date de début est après la date de fin.
     */
    public Session createSession(
            int session_id,
            String titre,
            String description,
            Date debut,
            Date fin,
            Mode mode
    ) {
        if (debut.after(fin)) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin.");
        }

        return new Session(session_id, titre, description, debut, fin);
    }
}
