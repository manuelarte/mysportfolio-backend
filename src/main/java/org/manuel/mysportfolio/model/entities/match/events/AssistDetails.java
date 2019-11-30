package org.manuel.mysportfolio.model.entities.match.events;

import javax.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class AssistDetails {

    @NotNull
    private String who;

    private BodyPart bodyPart;

}
