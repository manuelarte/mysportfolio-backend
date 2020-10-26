package org.manuel.mysportfolio.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

  private final AppUserRepository appUserRepository;
  private String errorMessage;

  @Override
  public void initialize(final UserExists constraintAnnotation) {
    this.errorMessage = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    final var byExternalId = appUserRepository.findByExternalId(value);
    if (byExternalId.isEmpty()) {
      context.buildConstraintViolationWithTemplate(errorMessage);
      return false;
    }
    return true;
  }
}
