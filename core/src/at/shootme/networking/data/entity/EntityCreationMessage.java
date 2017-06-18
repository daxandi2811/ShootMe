package at.shootme.networking.data.entity;

/**
 * Created by Nicole on 17.06.2017.
 */
public class EntityCreationMessage {

    private String entityName;
    private EntityBodyGeneralState bodyGeneralState;
    private String id;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityBodyGeneralState getBodyGeneralState() {
        return bodyGeneralState;
    }

    public void setBodyGeneralState(EntityBodyGeneralState bodyGeneralState) {
        this.bodyGeneralState = bodyGeneralState;
    }
}
