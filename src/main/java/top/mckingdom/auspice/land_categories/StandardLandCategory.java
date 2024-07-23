package top.mckingdom.auspice.land_categories;

import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.messenger.DefinedMessenger;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.utils.MessengerUtil;

public final class StandardLandCategory extends LandCategory {
    public StandardLandCategory(Namespace ns, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger) {
        super(ns, nameMessenger, descriptionMessenger);
    }

    public static final LandCategory NULL = reg("LAND_CATEGORY_NULL");
    public static final LandCategory WARFARE = reg("WARFARE");
    public static final LandCategory ECONOMICS = reg("ECONOMICS");
    public static final LandCategory INTERIOR = reg("INTERIOR");
    public static final LandCategory DIPLOMACY = reg("DIPLOMACY");

    private static LandCategory reg(String key) {
        LandCategory landCategory = new LandCategory(new Namespace("AuspiceAddon", key),
                MessengerUtil.createMessenger(new String[]{"lands", "land-category", key.toLowerCase().replace('_', '-'), "name"}, key),
                MessengerUtil.createMessenger(new String[]{"lands", "land-category", key.toLowerCase().replace('_', '-'), "description"}, "A land category" + key)
        );
        AuspiceAddon.get().getLandCategoryRegistry().register(landCategory);
        return landCategory;
    }

    public static void init() {

    }

}
