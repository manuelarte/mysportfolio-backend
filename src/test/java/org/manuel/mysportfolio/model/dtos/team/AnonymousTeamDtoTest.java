package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.config.AppConfig;

public class AnonymousTeamDtoTest {

    private static final ObjectMapper OBJECT_MAPPER = new AppConfig().objectMapper();

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(AnonymousTeamDto.class);
    }

}
