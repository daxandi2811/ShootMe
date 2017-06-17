package at.shootme.entity.player;

import at.shootme.SM;
import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.entity.player.Player.PlayerCreationMessage;
import at.shootme.networking.data.entity.EntityCreationMessage;

public class PlayerHandler extends AbstractEntityTypeHandler {

    public String getEntityName() {
        return Player.class.getSimpleName();
    }

    @Override
    protected PlayerCreationMessage createEntityCreationMessageInstance() {
        return new PlayerCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
        PlayerCreationMessage playerCreationMessage = (PlayerCreationMessage) message;
        playerCreationMessage.setAvailableJumps(((Player) entity).getAvailableJumps());
        playerCreationMessage.setTexturepath(((Player) entity).getTexturepath());
    }

    @Override
    protected Player createEntityInternal(EntityCreationMessage entityCreationMessage) {
        PlayerCreationMessage playerCreationMessage = (PlayerCreationMessage) entityCreationMessage;
        Player player = new Player();
        player.setTexturepath(playerCreationMessage.getTexturepath());
        player.init(entityCreationMessage.getBodyGeneralState().getPosition(), SM.world);
        entityCreationMessage.getBodyGeneralState().applyTo(player.getBody());
        return player;
    }
}
