package org.manuel.mysportfolio.model;

import java.util.Set;

@lombok.Getter
public enum Sport {

    FOOTBALL(SportType.THREE_A_SIDE, SportType.FIVE_A_SIDE, SportType.SIX_A_SIDE, SportType.SEVEN_A_SIDE, SportType.ELEVEN_A_SIDE),
    FUTSAL;

    private final Set<SportType> types;

    Sport(final Set<SportType> types) {
        this.types = types;
    }

    Sport(SportType... types) {
        this(Set.of(types));
    }

}
