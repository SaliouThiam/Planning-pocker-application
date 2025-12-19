package com.example.ppocker.dto;

import com.example.ppocker.model.PlanningMode;
import lombok.Data;
import java.util.List;

/**
 * DTO utilisé pour créer une session de Planning Poker.
 * <p>
 * Contient le nom de la session, le backlog (liste de fonctionnalités)
 * et le mode de calcul des votes.
 * </p>
 */
@Data
public class CreateSessionRequest {

    /** Nom de la session à créer. */
    private String name;

    /** Liste des fonctionnalités à estimer (backlog). */
    private List<String> backlog;

    /** Mode de calcul des votes (STRICT ou AVERAGE). */
    private PlanningMode mode;
}
