package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.PlayerDto;
import org.manuel.mysportfolio.model.entities.Player;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerToPlayerDtoTransformer implements Function<Player, PlayerDto> {

    @Override
    public PlayerDto apply(final Player player) {
        return player == null ? null : PlayerDto.builder()
                .id(player.getId().toString())
                .fullName(player.getFullName())
                .imageLink(player.getImageLink())
                .build();
    }

}
