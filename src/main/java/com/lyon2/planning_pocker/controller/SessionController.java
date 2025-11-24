package com.lyon2.planning_pocker.controller;

import com.lyon2.planning_pocker.dto.SessionRequest;
import com.lyon2.planning_pocker.model.Session;
import com.lyon2.planning_pocker.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST exposant les endpoints pour gérer les {@link Session}
 * de Planning Poker.
 *
 * <p>Ce contrôleur permet notamment :</p>
 * <ul>
 *     <li>la création d'une nouvelle session via un POST HTTP</li>
 *     <li>l'intégration avec {@link SessionService} pour encapsuler la logique métier</li>
 * </ul>
 *
 * <p>Les routes exposées sont préfixées par {@code /api/sessions}.</p>
 */
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    /**
     * Service métier pour gérer les sessions.
     */
    private final SessionService sessionService;

    /**
     * Constructeur du contrôleur, injecte le {@link SessionService}.
     *
     * @param sessionService service utilisé pour gérer les sessions
     */
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    /**
     * Endpoint pour créer une nouvelle session de Planning Poker.
     *
     * <p>Exemple de requête :</p>
     * <pre>
     * POST /api/sessions/add
     * Content-Type: application/json
     * {
     *     "titre": "Sprint 12",
     *     "description": "Backlog de fonctionnalités du sprint",
     *     "debut": "2025-11-24T10:00:00",
     *     "fin": "2025-11-24T12:00:00",
     *     "mode": "STRICT",
     *     "joueurs": ["Alice", "Bob"],
     *     "fonctionnalites": ["Login", "Dashboard"]
     * }
     * </pre>
     *
     * @param request objet DTO {@link SessionRequest} contenant les informations
     *                nécessaires à la création de la session
     * @return {@link ResponseEntity} contenant la session créée et persistée
     */
    @PostMapping("/add")
    public ResponseEntity<Session> addSession(@RequestBody SessionRequest request) {
        Session session = sessionService.createSession(request);
        return ResponseEntity.ok(session);
    }
}
