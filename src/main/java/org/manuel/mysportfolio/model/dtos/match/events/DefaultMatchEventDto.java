package org.manuel.mysportfolio.model.dtos.match.events;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.manuelarte.mysportfolio.model.documents.match.events.DefaultMatchEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class DefaultMatchEventDto implements MatchEventDto<DefaultMatchEvent> {

  private String id;

  private Map<String, Object> map = new HashMap<>();

  @JsonAnyGetter
  @SuppressWarnings("unused")
  public Object get(final String key) {
    return this.map.get(key);
  }

  @JsonAnySetter
  @SuppressWarnings("unused")
  public void set(final String key, final Object value) {
    this.map.put(key, value);
  }

  @Override
  public DefaultMatchEvent toMatchEvent() {
    final var defaultMatchEvent = new DefaultMatchEvent();
    defaultMatchEvent.setId(Optional.ofNullable(id).map(ObjectId::new).orElse(null));
    defaultMatchEvent.setMap(map);
    return defaultMatchEvent;
  }

}
