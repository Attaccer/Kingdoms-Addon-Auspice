package top.mckingdom.auspice;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.admin.land_category.CommandAdminLandCategory;
import top.mckingdom.auspice.commands.admin.relation_attribute.CommandAdminRelationAttribute;
import top.mckingdom.auspice.commands.general.land.CommandLand;
import top.mckingdom.auspice.commands.general.transfer_member.CommandTransferMember;
import top.mckingdom.auspice.configs.AuspiceConfig;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.configs.AuspicePlaceholder;
import top.mckingdom.auspice.data.land.LandCategoryMetaHandler;
import top.mckingdom.auspice.data.land.LandContractionMetaHandler;
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister;
import top.mckingdom.auspice.utils.permissions.RelationAttributeRegister;
import top.mckingdom.auspice.land.land_categories.LandCategoryRegistry;
import top.mckingdom.auspice.land.land_categories.StandardLandCategory;
import top.mckingdom.auspice.land.land_contractions.LandContractionRegistry;
import top.mckingdom.auspice.managers.land.BeaconEffectsManager;
import top.mckingdom.auspice.managers.land.BoatUseManager;
import top.mckingdom.auspice.managers.land.ElytraManager;
import top.mckingdom.auspice.managers.land.EnderPearlTeleportManager;
import top.mckingdom.auspice.services.ServiceBStats;
import top.mckingdom.auspice.utils.MessengerUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class AuspiceAddon extends JavaPlugin implements Addon {

    public static ServiceBStats B_STATS;

    private static AuspiceAddon instance;

    private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();
    private final LandCategoryRegistry landCategoryRegistry = new LandCategoryRegistry();
    private final LandContractionRegistry landContractionRegistry = new LandContractionRegistry();

    private static boolean enabled = false;

    public AuspiceAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        if (!isKingdomsLoaded()) return;

        landMetadataHandlers.addAll(Arrays.asList(
                LandCategoryMetaHandler.INSTANCE,
                LandContractionMetaHandler.INSTANCE
        ));
        landMetadataHandlers.forEach( metaHandler -> {
            Kingdoms.get().getMetadataRegistry().register(metaHandler);
        } );

        LanguageManager.registerMessenger(AuspiceLang.class);
        StandardLandCategory.init();
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

        B_STATS = new ServiceBStats(this, 22764);


        getLogger().info("Addon is enabling...");

        getLogger().info("Registering event listeners...");
        registerAllEvents();

        getLogger().info("Registering commands...");
        registerAllCommands();


        AuspicePlaceholder.init();
        MessengerUtil.lock();

        registerAddon();

        enabled = true;
    }

    @Override
    public void onDisable() {
        getLogger().info("Addon is disabling...");
        B_STATS.shutdown();
        signalDisable();
    }

    @Override
    public void reloadAddon() {
        getLogger().info("Registering event listeners...");
        registerAllEvents();

        getLogger().info("Registering commands...");
        registerAllCommands();

    }

    @Override
    public void uninstall() {
        getLogger().info("Removing auspice addon metadata...");
        KingdomMetadataRegistry.removeMetadata(Kingdoms.get().getDataCenter().getLandManager(), landMetadataHandlers);

        Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach(kingdom -> {

            kingdom.getGroup().getAttributes().values().forEach(attrSet -> {
                attrSet.remove(RelationAttributeRegister.BEACON_EFFECTS);
                attrSet.remove(RelationAttributeRegister.ENDER_PEARL_TELEPORT);
                attrSet.remove(RelationAttributeRegister.DIRECTLY_TRANSFER_MEMBERS);
            });

            kingdom.getRanks().forEach(rank -> {
                rank.getPermissions().remove(KingdomPermissionRegister.PERMISSION_TRANSFER_MEMBERS);
            });

            ;

        });

        //TODO get all lands of the server



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


    public void registerAllEvents() {

        if (KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()) {
            getServer().getPluginManager().registerEvents(new BeaconEffectsManager(), this);
            EntityPotionEffectEvent.getHandlerList().unregister(Kingdoms.get());
        }

        getServer().getPluginManager().registerEvents(new EnderPearlTeleportManager(), this);
        PlayerTeleportEvent.getHandlerList().unregister(Kingdoms.get());

        getServer().getPluginManager().registerEvents(new BoatUseManager(), this);

        if (AuspiceConfig.PROTECTED_ELYTRA.getManager().getBoolean()) {
            getServer().getPluginManager().registerEvents(new ElytraManager(), this);
        }

    }

    public void registerAllCommands() {

        if (AuspiceConfig.MEMBER_TRANSFER_ENABLED.getManager().getBoolean()) {
            new CommandTransferMember();
        }
        new CommandAdminLandCategory(CommandAdmin.getInstance());
        new CommandAdminRelationAttribute(CommandAdmin.getInstance());
        new CommandLand();

    }


    /**
     * Only use for this addon
     */
    public static Namespace buildNS(String s) {
        return new Namespace("AuspiceAddon", s);
    }

    public LandContractionRegistry getLandContractionRegistry() {
        return landContractionRegistry;
    }
}
