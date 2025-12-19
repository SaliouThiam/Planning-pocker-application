package com.example.ppocker.controller;

import com.example.ppocker.dto.*;
import com.example.ppocker.model.PlanningMode;
import com.example.ppocker.model.Session;
import com.example.ppocker.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur REST pour gérer les sessions de Planning Poker.
 * <p>
 * Permet de créer une session, rejoindre une session existante,
 * récupérer l'état d'une session et restaurer une session depuis un JSON.
 * </p>
 */
@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {

    /** Service gérant la logique métier des sessions. */
    private final SessionService sessionService;

    /**
     * Constructeur.
     *
     * @param sessionService Service des sessions injecté par Spring.
     */
    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Crée une nouvelle session.
     *
     * @param req DTO contenant le nom, le backlog et le mode de la session.
     * @return La session créée avec son identifiant, backlog et mode.
     */
    @PostMapping
    public ResponseEntity<CreateSessionResponse> create(@RequestBody CreateSessionRequest req) {

        PlanningMode mode = req.getMode() != null
                ? req.getMode()
                : PlanningMode.STRICT;

        Session s = sessionService.createSession(
                req.getName(),
                req.getBacklog(),
                mode
        );

        CreateSessionResponse resp = new CreateSessionResponse();
        resp.setId(s.getId());
        resp.setName(s.getName());
        resp.setBacklog(s.getBacklog());
        resp.setMode(s.getMode());

        return ResponseEntity.ok(resp);
    }

    /**
     * Permet à un joueur de rejoindre une session existante.
     *
     * @param id  Identifiant de la session.
     * @param req DTO contenant le pseudo du joueur.
     * @return Statut de l'opération et informations sur le joueur rejoint.
     */
    @PostMapping("/{id}/join")
    public ResponseEntity<?> join(@PathVariable String id, @RequestBody JoinRequest req) {
        boolean ok = sessionService.joinSession(id, req.getPseudo());
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of(
                "status","joined",
                "pseudo", req.getPseudo(),
                "sessionId", id
        ));
    }

    /**
     * Récupère une session par son identifiant.
     *
     * @param id Identifiant de la session.
     * @return La session si trouvée, sinon 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return sessionService.getSession(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Récupère une session sous forme Optional.
     *
     * @param id Identifiant de la session.
     * @return Optional contenant la session (peut être vide).
     */
    public ResponseEntity<Optional<Session>> getSession(@PathVariable String id) {
        Optional<Session> s = sessionService.getSession(id);
        return ResponseEntity.ok(s);
    }

    /**
     * Permet de restaurer une session depuis un objet JSON.
     *
     * @param imported Session JSON importée.
     * @return La session restaurée ou une erreur si l'import est invalide.
     */
    @PostMapping("/import")
    public ResponseEntity<?> importSession(@RequestBody Session imported) {
        if (imported == null || imported.getId() == null) {
            return ResponseEntity.badRequest().body("Session JSON invalide (id manquant).");
        }
        Session s = sessionService.restoreSession(imported);
        return ResponseEntity.ok(s);
    }
}
