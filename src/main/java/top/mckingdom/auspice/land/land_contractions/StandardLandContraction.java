package top.mckingdom.auspice.land.land_contractions;

import org.bukkit.Bukkit;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.utils.MessengerUtil;

import java.util.Locale;

public class StandardLandContraction extends LandContraction{

    public static final StandardLandContraction MANAGE_CONTRACTIONS = register(AuspiceAddon.buildNS("MANAGE_CONTRACTIONS"));
    public static final StandardLandContraction TURRETS = register(AuspiceAddon.buildNS("TURRETS"));
    public static final StandardLandContraction FARMING = register(AuspiceAddon.buildNS("FARMING"));
    public static final StandardLandContraction BUILD = register(AuspiceAddon.buildNS("BUILD"));
    public static final StandardLandContraction SHOP;


    private DefinedMessenger nameMessenger;

    public StandardLandContraction(Namespace namespace, DefinedMessenger nameMessenger) {
        super(namespace);
        this.nameMessenger = nameMessenger;
    }

    @Override
    public String getName(SupportedLanguage lang) {
        return null;
    }

    public static StandardLandContraction register(Namespace ns) {
        String key = ns.getKey().toLowerCase(Locale.ENGLISH).replace('_', '-');
        StandardLandContraction c = new StandardLandContraction(ns, MessengerUtil.createMessenger(new String[]{"lands", "land-contraction", key}, key));
        AuspiceAddon.get().getLandContractionRegistry().register(c);
        return c;
    }

    public static void init() {

    }

    static {
        if (Bukkit.getPluginManager().getPlugin("QuickShop") != null) {
            SHOP = register(AuspiceAddon.buildNS("SHOP"));
        } else {
            SHOP = null;
        }
    }


}
