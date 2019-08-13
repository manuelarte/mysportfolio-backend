package org.manuel.mysportfolio.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.net.URL;
import java.util.UUID;

@Document(collection = "teams")
@lombok.Data
@lombok.AllArgsConstructor
public class Team {

    @Id
    private UUID id;

    @NotEmpty
    @Max(30)
    private String name;

    @org.hibernate.validator.constraints.URL
    private URL imageLink;

}
