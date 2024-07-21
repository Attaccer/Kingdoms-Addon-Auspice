package top.mckingdom.auspice.managers;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.group.upgradable.MiscUpgrade;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.libs.xseries.particles.ParticleDisplay;
import org.kingdoms.locale.KingdomsLang;
import top.mckingdom.auspice.entitlements.RelationAttributeRegister;

public class EnderPearlTeleportManager implements Listener {
//    private final MiscUpgradeManager a = new MiscUpgradeManager();

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public final void onPearlTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
//            HandlerList handlerList = event.getHandlers();
//            handlerList.unregister(a);
            Player player;
            KingdomPlayer kPlayer = KingdomPlayer.getKingdomPlayer(player = event.getPlayer());
            Land land;
            if ((land = Land.getLand(event.getTo())) != null && land.isClaimed()) {
                Kingdom kingdom;
                if ((kingdom = land.getKingdom()).getUpgradeLevel(MiscUpgrade.ANTI_TRAMPLE) >= 3) {
                    Kingdom var5 = kPlayer.getKingdom();
                    if (!RelationAttributeRegister.ENDER_PEARL_TELEPORT.hasAttribute(kingdom, var5)) {
                        event.setCancelled(true);
                        ParticleDisplay.of(Particle.CLOUD).withCount(10).spawn(event.getTo());
                        KingdomsLang.LANDS_ENDER_PEARL_PROTECTION.sendError(player);
                    } else {
                        event.setCancelled(false);
                    }
                }
            }
        }
    }

}
