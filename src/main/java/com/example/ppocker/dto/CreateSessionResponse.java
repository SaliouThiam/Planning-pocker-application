package com.example.ppocker.dto;

import com.example.ppocker.model.PlanningMode;
import lombok.Data;
import java.util.List;

/**
 * DTO utilisé pour répondre à la création d'une session de Planning Poker.
 * <p>
 * Contient l'identifiant généré de la session, le nom, le backlog et le mode.
 * </p>
 */
@Data
public class CreateSessionResponse {

    /** Identifiant unique de la session. */
    private String id;

    /** Nom de la session. */
    private String name;

    /** Liste des fonctionnalités du backlog. */
    private List<String> backlog;

    /** Mode de calcul des votes (STRICT ou AVERAGE). */
    private PlanningMode mode;
}
