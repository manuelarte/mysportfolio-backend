package org.manuel.mysportfolio.model.entities.match;

import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Performance {

    @Digits(integer = 10, fraction = 2)
    @Size(max = 10, min = 0)
    @NotNull
    private BigDecimal performance;
    private String description;

}
