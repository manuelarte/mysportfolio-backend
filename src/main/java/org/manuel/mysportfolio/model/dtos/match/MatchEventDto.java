package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.NoArgsConstructor
@lombok.Builder(toBuilder = true)
public class MatchEventDto {

    @NotEmpty
    private final Map<String, Object> properties = new HashMap<>();

    @JsonAnyGetter
    @SuppressWarnings("unused")
    public Map<String, Object> getProperties() {
        return properties;
    }

    public Object get(final String key) {
        return properties.get(key);
    }

    @JsonAnySetter
    @SuppressWarnings("unused")
    public void set(final String key, final Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    @JsonIgnore
    public String getType() {
        return this.properties.get("type").toString();
    }

    @AssertTrue
    @SuppressWarnings("unused")
    public boolean containsType() {
        return this.properties.containsKey("type");
    }
}
