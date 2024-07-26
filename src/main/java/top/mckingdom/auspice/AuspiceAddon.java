package top.mckingdom.auspice;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.general.transfer_member.CommandTransferMember;
import top.mckingdom.auspice.configs.AuspiceConfig;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.configs.CustomConfigValidators;
import top.mckingdom.auspice.costs.StandardCostType;
import top.mckingdom.auspice.data.land.LandCategoryPlaceholder;
import top.mckingdom.auspice.entitlements.KingdomPermissionRegister;
import top.mckingdom.auspice.entitlements.RelationAttributeRegister;
import top.mckingdom.auspice.land_categories.LandCategoryRegistry;
import top.mckingdom.auspice.land_categories.StandardLandCategory;
import top.mckingdom.auspice.managers.BeaconEffectsManager;
import top.mckingdom.auspice.managers.BoatUseManager;
import top.mckingdom.auspice.managers.EnderPearlTeleportManager;
import top.mckingdom.auspice.services.ServiceBStats;
import top.mckingdom.auspice.utils.MessengerUtil;
import static top.mckingdom.auspice.data.land.LandCategoryData.getCategory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class AuspiceAddon extends JavaPlugin implements Addon {

    public static ServiceBStats BSTATS;

    private static AuspiceAddon instance;

    private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();
    private final LandCategoryRegistry landCategoryRegistry = new LandCategoryRegistry();

    private static boolean enabled = false;

    public AuspiceAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        if (!isKingdomsLoaded()) return;
        CustomConfigValidators.init();

        LanguageManager.registerMessenger(AuspiceLang.class);
        StandardLandCategory.init();
        StandardCostType.init();
        KingdomPermissionRegister.init();
        RelationAttributeRegister.init();

        getLogger().info("Addon is loading...");

    }


    @Override
    public void onEnable() {

        if (!isKingdomsEnabled()) {
            getLogger().severe("Kingdoms plugin didn't load correctly, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        BSTATS = new ServiceBStats(this, 22764);


        getLogger().info("Addon is enabling...");

        if (KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()) {
            getServer().getPluginManager().registerEvents(new BeaconEffectsManager(), this);
            EntityPotionEffectEvent.getHandlerList().unregister(Kingdoms.get());
        }
        getServer().getPluginManager().registerEvents(new EnderPearlTeleportManager(), this);
        PlayerTeleportEvent.getHandlerList().unregister(Kingdoms.get());
        getServer().getPluginManager().registerEvents(new BoatUseManager(), this);


        if (AuspiceConfig.MEMBER_TRANSFER_ENABLED.getManager().getBoolean()) {
            new CommandTransferMember();
        }

//        LandCategoryPlaceholder.init();


        MessengerUtil.lock();

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
        new CommandTransferMember();

    }

    @Override
    public void uninstall() {
        getLogger().info("Removing auspice addon metadata info...");
        KingdomMetadataRegistry.removeMetadata(Kingdoms.get().getDataCenter().getLandManager(), landMetadataHandlers);

        Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach(kingdom -> {     //处理王国的数据

            kingdom.getGroup().getAttributes().values().forEach(attrSet -> {    //去除每种外交关系里面的特定外交属性
                attrSet.remove(RelationAttributeRegister.BEACON_EFFECTS);
                attrSet.remove(RelationAttributeRegister.ENDER_PEARL_TELEPORT);
                attrSet.remove(RelationAttributeRegister.DIRECTLY_TRANSFER_MEMBERS);
            });

            kingdom.getRanks().forEach(rank -> {     //去除每个职级里面的特定王国权限
                rank.getPermissions().remove(KingdomPermissionRegister.PERMISSION_TRANSFER_MEMBERS);
            });

        });

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

    public static AuspiceAddon get() {
        return instance;
    }

    public static boolean isAuspiceAddonEnabled() {
        return enabled;
    }

    public LandCategoryRegistry getLandCategoryRegistry() {
        return this.landCategoryRegistry;
    }



}
