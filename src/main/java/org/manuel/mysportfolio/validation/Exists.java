package org.manuel.mysportfolio.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.manuel.mysportfolio.model.entities.match.Match;

@Documented
@Constraint(validatedBy = {ExistsValidator.class, ExistsIterableValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

  String message() default "Entity not found";

  Class<?> entity() default Match.class;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
