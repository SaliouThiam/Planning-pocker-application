package com.lyon2.planning_pocker.service;

import com.lyon2.planning_pocker.dto.SessionRequest;
import com.lyon2.planning_pocker.model.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests d'intégration pour {@link SessionService} utilisant la base H2.
 *
 * <p>Ces tests vérifient la création et la persistance des sessions
 * directement dans la base de données en mémoire H2.</p>
 */
@SpringBootTest
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    /**
     * Test de création d'une session avec joueurs et fonctionnalités.
     */
    @Test
    void testCreateSession_withPlayersAndFonctionnalites() {
        SessionRequest request = SessionRequest.builder()
                .titre("Sprint 1")
                .description("Backlog initial")
                .debut(LocalDateTime.of(2025, 11, 24, 10, 0))
                .fin(LocalDateTime.of(2025, 11, 24, 12, 0))
                .joueurs(List.of("Alice", "Bob"))
                .fonctionnalites(List.of("Login", "Dashboard"))
                .build();

        Session created = sessionService.createSession(request);

        assertNotNull(created);
        assertEquals("Sprint 1", created.getTitre());
        assertEquals(2, created.getJoueur().size(), "Il doit y avoir 2 joueurs");
        assertEquals(2, created.getFonctionnalite().size(), "Il doit y avoir 2 fonctionnalités");
        assertNotNull(created.getSessionCode(), "Le code de session doit être généré");
    }

    /**
     * Test de création d'une session sans joueurs ni fonctionnalités.
     */
    /*
    @Test
    void testCreateSession_withoutPlayersOrFonctionnalites() {
        SessionRequest request = SessionRequest.builder()
                .titre("Sprint 2")
                .description("Backlog vide")
                .debut(LocalDateTime.of(2025, 11, 25, 10, 0))
                .fin(LocalDateTime.of(2025, 11, 25, 12, 0))
                .build();

        Session created = sessionService.createSession(request);

        assertNotNull(created);
        assertEquals("Sprint 2", created.getTitre());
        assertEquals(0, created.getJoueur().size(), "Aucun joueur");
        assertEquals(0, created.getFonctionnalite().size(), "Aucune fonctionnalité");
        assertNotNull(created.getSessionCode(), "Le code de session doit être généré");
    }

     */
}
