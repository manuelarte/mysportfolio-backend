package org.manuel.mysportfolio.model.entities.player;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.BaseEntity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
@lombok.Data
public class PlayerProfile extends BaseEntity {

  @NotNull
  @Indexed(unique = true)
  private String externalId;

  @NotNull
  private Map<@PastOrPresent Year, @Valid PlayerProfileSportInfo> info = new HashMap<>();

  public PlayerProfile(ObjectId id, Long lockVersion, String externalId) {
    super(id, lockVersion);
    this.externalId = externalId;
  }

  public PlayerProfile(String externalId) {
    this(null, null, externalId);
  }

}
