package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultMatchEvent implements MatchEvent {

    private ObjectId id;

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

}
