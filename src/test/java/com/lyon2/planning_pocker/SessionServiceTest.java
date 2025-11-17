package com.lyon2.planning_pocker;

import com.lyon2.planning_pocker.model.Mode;
import com.lyon2.planning_pocker.model.Session;
import com.lyon2.planning_pocker.service.SessionService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

/**
 * Test unitaire pour la classe {@link SessionService}.
 * Cette classe contient les tests pour vérifier le comportement de la création de session.
 */
public class SessionServiceTest {

    /**
     * Teste la création d'une session avec des dates valides.
     * Vérifie que la session est bien créée et que les dates de début et de fin sont correctes.
     */
    @Test
    void testCreateSession_ValidDates() {
        SessionService service = new SessionService();

        Date debut = new Date(1000000);  // 1 sec
        Date fin = new Date(2000000);    // 2 sec

        // Création de la session
        Session s = service.createSession(
                1,
                "Titre",
                "Description",
                debut,
                fin,
                Mode.STRICT
        );

        // Vérification des valeurs
        assertNotNull(s);
        assertEquals(debut, s.getDebut());
        assertEquals(fin, s.getFin());
    }
}
