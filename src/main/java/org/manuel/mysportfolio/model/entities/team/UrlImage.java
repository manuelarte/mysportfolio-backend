package org.manuel.mysportfolio.model.entities.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UrlImage implements TeamImage {

    @org.hibernate.validator.constraints.URL
    @Size(max = 200)
    @NotNull
    private String url;

}
