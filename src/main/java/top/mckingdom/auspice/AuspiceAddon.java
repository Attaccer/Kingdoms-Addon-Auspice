package top.mckingdom.auspice;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.general.CommandTransferMember;
import top.mckingdom.auspice.configs.CustomConfigValidators;
import top.mckingdom.auspice.costs.StandardCostType;
import top.mckingdom.auspice.entitlements.KingdomPermissionRegister;
import top.mckingdom.auspice.entitlements.RelationAttributeRegister;
import top.mckingdom.auspice.managers.BeaconEffectsManager;
import top.mckingdom.auspice.managers.BoatUseManager;
import top.mckingdom.auspice.managers.EnderPearlTeleportManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class AuspiceAddon extends JavaPlugin implements Addon {

    private static AuspiceAddon instance;

    private final Set<KingdomMetadataHandler> metadataHandlers = new HashSet<>();

    private static boolean enabled = false;

    public AuspiceAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        if (!isKingdomsLoaded()) return;
        CustomConfigValidators.init();

        getLogger().info("Addon is loading...");

    }


    @Override
    public void onEnable() {

        if (!isKingdomsEnabled()) {
            getLogger().severe("Kingdoms plugin didn't load correctly, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        KingdomPermissionRegister.init();
        RelationAttributeRegister.init();
        getLogger().info("Addon is enabling...");

        if (KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()) {
            getServer().getPluginManager().registerEvents(new BeaconEffectsManager(), this);
        }
        getServer().getPluginManager().registerEvents(new BoatUseManager(), this);
        getServer().getPluginManager().registerEvents(new EnderPearlTeleportManager(), this);

        new CommandTransferMember();

        StandardCostType.init();

        registerAddon();

        enabled = true;
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
    public void uninstall() {
        getLogger().info("Removing peace treaties metadata info...");
        KingdomMetadataRegistry.removeMetadata(Kingdoms.get().getDataCenter().getLandManager(), metadataHandlers);
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

    public static boolean isAuspiceEnabled() {
        return enabled;
    }




}
