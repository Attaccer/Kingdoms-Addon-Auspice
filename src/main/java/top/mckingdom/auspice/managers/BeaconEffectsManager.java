package top.mckingdom.auspice.managers;


import top.mckingdom.auspice.permissions.RelationAttributeAutoRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.managers.land.LandEffectsManager;

public class BeaconEffectsManager implements Listener {
    LandEffectsManager.BeaconManager a = new LandEffectsManager.BeaconManager();

    @EventHandler
    public void whenPlayerInBeacon(EntityPotionEffectEvent event) {
        if (event.getCause() == EntityPotionEffectEvent.Cause.BEACON) {
            HandlerList handlerList = event.getHandlers();
            handlerList.unregister(a);
            if (event.getEntity() instanceof Player) {
                KingdomPlayer player = KingdomPlayer.getKingdomPlayer((Player)event.getEntity());
                Land land = Land.getLand(event.getEntity().getLocation());
                if (land != null && land.isClaimed()) {
                    event.setCancelled(!land.getKingdom().hasAttribute(player.getKingdom(), RelationAttributeAutoRegister.BEACON_EFFECTS));
                }
            }
        }
    }


}