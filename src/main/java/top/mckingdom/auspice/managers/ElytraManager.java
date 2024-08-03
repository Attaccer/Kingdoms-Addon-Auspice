package top.mckingdom.auspice.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.events.lands.LandChangeEvent;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.entitlements.RelationAttributeRegister;
import top.mckingdom.land_protection.configs.LPLang;

public class ElytraManager implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onPlayerFlyBy(LandChangeEvent event) {

        Player player = event.getPlayer();
        if (player.isGliding()) {
            KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(player);
            Land land = event.getToLand();
            if (land != null && (!RelationAttributeRegister.ELYTRA.hasAttribute(land.getKingdom(), kingdomPlayer.getKingdom()))) {
                player.setGliding(false);
                AuspiceLang.LANDS_ELYTRA_PROTECTION.sendError(player);
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerFly(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        if (event.isGliding()) {
            if (entity instanceof Player player) {
                KingdomPlayer kingdomPlayer = KingdomPlayer.getKingdomPlayer(player);
                Land land = Land.getLand(entity.getLocation());
                if (land != null && (!RelationAttributeRegister.ELYTRA.hasAttribute(land.getKingdom(), kingdomPlayer.getKingdom()))) {
                    Bukkit.getScheduler().runTaskLater(AuspiceAddon.get(), () -> player.setGliding(false), 1);
                    AuspiceLang.LANDS_ELYTRA_PROTECTION.sendError(player);
                }
            }
        }
    }


}
