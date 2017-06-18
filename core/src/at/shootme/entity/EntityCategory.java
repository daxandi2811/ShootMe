package at.shootme.entity;

import java.util.Arrays;

/**
 * Created by Nicole on 28.05.2017.
 */
public enum EntityCategory {
    PLAYER,
    PLATFORM,
    PICKUP,
    SHOT,;

    public boolean isOneOf(EntityCategory... categories) {
        return Arrays.stream(categories).anyMatch(entityCategory -> entityCategory == this);
    }
}
