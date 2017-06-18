package at.shootme.networking.data.entity;

import at.shootme.beans.HorizontalMovementState;
import at.shootme.beans.ViewDirection;

/**
 * Created by Nicole on 18.06.2017.
 */
public class PlayerStateChangeMessage extends EntityStateChangeMessage {

    private int availableJumps;
    private ViewDirection viewDirection;
    private HorizontalMovementState horizontalMovementState;

    public int getAvailableJumps() {
        return availableJumps;
    }

    public void setAvailableJumps(int availableJumps) {
        this.availableJumps = availableJumps;
    }

    public ViewDirection getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(ViewDirection viewDirection) {
        this.viewDirection = viewDirection;
    }

    public HorizontalMovementState getHorizontalMovementState() {
        return horizontalMovementState;
    }

    public void setHorizontalMovementState(HorizontalMovementState horizontalMovementState) {
        this.horizontalMovementState = horizontalMovementState;
    }
}
