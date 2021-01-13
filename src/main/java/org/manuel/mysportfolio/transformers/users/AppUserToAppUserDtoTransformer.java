package org.manuel.mysportfolio.transformers.users;

import io.github.manuelarte.mysportfolio.model.documents.user.AppUser;
import io.github.manuelarte.mysportfolio.model.dtos.user.AppUserDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class AppUserToAppUserDtoTransformer implements Function<AppUser, AppUserDto> {

  @Override
  public AppUserDto apply(final AppUser appUser) {
    return AppUserDto.builder()
        .id(appUser.getId())
        .version(appUser.getVersion())
        //.email(appUser.getEmail())
        .externalId(appUser.getExternalId())
        .fullName(appUser.getFullName())
        .build();
  }

}
