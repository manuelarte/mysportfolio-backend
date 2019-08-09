package org.manuel.mysportfolio.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Match {

    @Id
    private UUID id;

}
