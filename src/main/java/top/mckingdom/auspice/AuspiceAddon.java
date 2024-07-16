package top.mckingdom.auspice;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.commands.general.resourcepoints.transfer.CommandResourcePointsTransfer;
import org.kingdoms.config.KingdomsConfig;
import top.mckingdom.auspice.commands.general.CommandTransferMember;
import top.mckingdom.auspice.costs.StandardCostType;
import top.mckingdom.auspice.managers.BeaconEffectsManager;
import top.mckingdom.auspice.managers.BoatUseManager;
import top.mckingdom.auspice.managers.EnderPearlTeleportManager;
import top.mckingdom.auspice.permissions.KingdomPermissionAutoRegister;
import top.mckingdom.auspice.permissions.RelationAttributeAutoRegister;

import java.io.File;

public final class AuspiceAddon extends JavaPlugin implements Addon {

    private static AuspiceAddon instance;

    public AuspiceAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        if (!isKingdomsLoaded()) return;
        getLogger().info("Addon is loading...");

    }

    @Override
    public void onEnable() {

        if (!isKingdomsEnabled()) {
            getLogger().severe("Kingdoms plugin didn't load correctly, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


        KingdomPermissionAutoRegister.init();
        RelationAttributeAutoRegister.init();
        getLogger().info("Addon is enabling...");

        if (KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()) {
            getServer().getPluginManager().registerEvents(new BeaconEffectsManager(), this);
        }
        getServer().getPluginManager().registerEvents(new BoatUseManager(), this);
        getServer().getPluginManager().registerEvents(new EnderPearlTeleportManager(), this);

        new CommandTransferMember();

        StandardCostType.init();

        registerAddon();

    }

    @Override
    public void onDisable() {
        getLogger().info("Addon is disabling...");
        signalDisable();
        // Plugin shutdown logic
    }

    @Override
    public void reloadAddon() {

    }

    @Override
    public String getAddonName() {
        return "auspice-addon";
    }

    @NonNull
    @Override
    public File getFile() {
        return super.getFile();
    }

    public static AuspiceAddon getInstance() {
        return instance;
    }






}
