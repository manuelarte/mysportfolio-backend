package org.manuel.mysportfolio.validations;

import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

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
