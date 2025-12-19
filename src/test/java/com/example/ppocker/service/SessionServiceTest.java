package com.example.ppocker.service;

import com.example.ppocker.model.PlanningMode;
import com.example.ppocker.model.Session;
import com.example.ppocker.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SessionServiceTest {

    private SessionService sessionService;

    @BeforeEach
    void setup() {
        // On mock le SimpMessagingTemplate car on ne teste pas le WebSocket ici
        SimpMessagingTemplate messagingTemplate = mock(SimpMessagingTemplate.class);
        sessionService = new SessionService(messagingTemplate);
    }

    @Test
    void testSetVote() {
        // Créer une session
        Session s = sessionService.createSession("TestSession", List.of("F1", "F2"), PlanningMode.STRICT);

        String pseudo="Alice";
        // Ajouter un joueur
        Player p = new Player();
        p.setPseudo(pseudo);
        s.getPlayers().add(p);

        // Vérifier que le vote initial est null ou 0
        assertFalse(s.getVotes().containsKey("Alice"));

        // Appliquer le vote
        sessionService.setVote(s.getId(), "Alice", 5);

        // Vérifier que le vote est enregistré
        assertEquals(5, s.getVotes().get("Alice"));
    }

    @Test
    void testNextFeature() {
        // Créer une session avec backlog
        List<String> backlog = new ArrayList<>();
        backlog.add("Feature1");
        backlog.add("Feature2");

        Session s = sessionService.createSession("TestSession", backlog, PlanningMode.STRICT);
        s.getVotes().put("Alice", 3);
        s.getVotes().put("Bob", 5);
        s.getVotes().put("RESULT", 5);

        s.setCurrentIndex(0);
        s.setRevealed(true);

        // Appeler nextFeature
        sessionService.nextFeature(s.getId());

        // Vérifier que l'index a avancé
        assertEquals(1, s.getCurrentIndex());

        // Vérifier que revealed est false
        assertFalse(s.isRevealed());

        // Vérifier que les votes sont reset
        assertEquals(0, s.getVotes().get("Alice"));
        assertEquals(0, s.getVotes().get("Bob"));
        assertFalse(s.getVotes().containsKey("RESULT"));
    }

    @Test
    void testGenerateCode() throws Exception {
        // La méthode generateCode est private, on peut tester indirectement via createSession
        Session s = sessionService.createSession("SessionCode", null, PlanningMode.STRICT);

        String code = s.getId();

        // Vérifier que le code n'est pas null et longueur = 5
        assertNotNull(code);
        assertEquals(5, code.length());

        // Vérifier que le code contient uniquement les caractères valides
        String validChars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        for (char c : code.toCharArray()) {
            assertTrue(validChars.indexOf(c) >= 0);
        }
    }
}
