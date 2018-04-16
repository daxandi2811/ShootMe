package at.shootme.entity.pickups;

import java.security.PrivateKey;

/**
 * Created by Nicole on 17.06.2017.
 */
public enum PickupType {
    SPEED_UP(5f),
    TRIPLE_JUMP(6f),
    SPECIAL_SHOT(4f);

    float duration;

    PickupType(float duration){
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }
}
