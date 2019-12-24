package org.manuel.mysportfolio.model.entities.match.events;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultMatchEvent implements MatchEvent {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
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
