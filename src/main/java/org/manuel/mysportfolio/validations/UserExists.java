package org.manuel.mysportfolio.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.manuel.mysportfolio.model.entities.user.AppUser;

@Documented
@Constraint(validatedBy = {UserExistsValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExists {

  Class<?> value() default AppUser.class;

  String message() default "User not found";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
