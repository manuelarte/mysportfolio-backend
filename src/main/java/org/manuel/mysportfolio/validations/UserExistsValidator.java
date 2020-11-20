package org.manuel.mysportfolio.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.repositories.AppUserRepository;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

  private final UserIdProvider userIdProvider;
  private final AppUserRepository appUserRepository;
  private String errorMessage;

  @Override
  public void initialize(final UserExists constraintAnnotation) {
    this.errorMessage = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    final String theValue;
    if ("me".equals(value)) {
      theValue = userIdProvider.getUserId();
    } else {
      theValue = value;
    }
    final var byExternalId = appUserRepository.findByExternalId(theValue);
    if (byExternalId.isEmpty()) {
      context.buildConstraintViolationWithTemplate(errorMessage);
      return false;
    }
    return true;
  }
}
