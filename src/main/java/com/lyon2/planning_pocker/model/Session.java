package com.lyon2.planning_pocker.model;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    private int session_id;
    private String titre;
    private String description;

    private Date debut;
    private Date fin;
    private Mode mode=Mode.STRICT;

    //private Backlog backlog;

    private List<Joueur> joueurs;

    public Session(int sessionId, String titre, String description, Date debut, Date fin) {
        this.session_id = sessionId;
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.mode = Mode.STRICT;
    }
}
