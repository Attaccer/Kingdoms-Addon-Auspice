package top.mckingdom.auspice.managers.land;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.kingdoms.constants.player.KingdomPlayer;
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister;

public class BoatUseManager implements Listener {

    @EventHandler
    public void onInteractBoat(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Boat) {
            KingdomPlayer player = KingdomPlayer.getKingdomPlayer(event.getPlayer());
            if (!player.hasPermission(KingdomPermissionRegister.PERMISSION_USE_BOATS)) {
                event.setCancelled(true);
                KingdomPermissionRegister.PERMISSION_USE_BOATS.sendDeniedMessage(event.getPlayer());
            }
        }
    }



}
