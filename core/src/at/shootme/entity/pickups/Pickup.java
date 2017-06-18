package at.shootme.entity.pickups;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import at.shootme.entity.player.Player;

/**
 * Created by Nicole on 18.06.2017.
 */
public interface Pickup extends Entity, Drawable {

    void pickedUpBy(Player player);

    default EntityCategory getCategory() {
        return EntityCategory.PICKUP;
    }
}
