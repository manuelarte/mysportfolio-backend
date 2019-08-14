package org.manuel.mysportfolio.model.entities;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.UUID;

@Document(collection = "players")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Player {

    @Id
    private ObjectId id;

    @NotNull
    @Max(200)
    private String fullName;

    /**
     * In Google it's the sub. An identifier for the user, unique among all Google accounts and never reused
     */
    @NotNull
    private String externalId;

    @org.hibernate.validator.constraints.URL
    private URL imageLink;

}
