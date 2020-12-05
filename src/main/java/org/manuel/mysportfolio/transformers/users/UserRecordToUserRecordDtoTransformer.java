package org.manuel.mysportfolio.transformers.users;

import com.google.firebase.auth.UserRecord;
import io.github.manuelarte.mysportfolio.model.dtos.user.UserRecordDto;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class UserRecordToUserRecordDtoTransformer implements Function<UserRecord, UserRecordDto> {

  @Override
  public UserRecordDto apply(final UserRecord userRecord) {
    return UserRecordDto.builder()
        .uid(userRecord.getUid())
        .displayName(userRecord.getDisplayName())
        .photoUrl(userRecord.getPhotoUrl())
        .build();
  }
}
