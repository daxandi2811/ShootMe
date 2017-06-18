package at.shootme.networking.data.entity;

/**
 * Created by Nicole on 17.06.2017.
 */
public class EntityStateChangeMessage {
    private String entityId;

    private EntityBodyGeneralState entityBodyGeneralState;

    public EntityStateChangeMessage() {
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public EntityBodyGeneralState getEntityBodyGeneralState() {
        return entityBodyGeneralState;
    }

    public void setEntityBodyGeneralState(EntityBodyGeneralState entityBodyGeneralState) {
        this.entityBodyGeneralState = entityBodyGeneralState;
    }
}
