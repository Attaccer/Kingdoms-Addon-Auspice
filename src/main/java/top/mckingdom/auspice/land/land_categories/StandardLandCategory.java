package top.mckingdom.auspice.land.land_categories;

import org.bukkit.plugin.RegisteredListener;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.locale.SupportedLanguage;
import org.kingdoms.locale.messenger.DefinedMessenger;
import top.mckingdom.auspice.AuspiceAddon;
import top.mckingdom.auspice.utils.MessengerUtil;

import java.util.Locale;

public class StandardLandCategory extends LandCategory {

    public static final LandCategory NONE = reg("NONE");
    public static final LandCategory WARFARE = reg("WARFARE");
    public static final LandCategory ECONOMICS = reg("ECONOMICS");
    public static final LandCategory INTERIOR = reg("INTERIOR");
    public static final LandCategory DIPLOMACY = reg("DIPLOMACY");

    private final DefinedMessenger nameMessenger;
    private final DefinedMessenger descriptionMessenger;
    private final DefinedMessenger loreMessenger;

    public StandardLandCategory(Namespace ns, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger, DefinedMessenger loreMessenger) {
        super(ns);
        this.nameMessenger = nameMessenger;
        this.descriptionMessenger = descriptionMessenger;
        this.loreMessenger = loreMessenger;

    }


    public static void init() {

    }
    private static LandCategory reg(String key) {
        return register(AuspiceAddon.buildNS(key));
    }

    public static StandardLandCategory register(Namespace ns) {
        String key = ns.getKey().toLowerCase(Locale.ENGLISH).replace('_', '-');
        return register(ns,
                MessengerUtil.createMessenger(new String[]{"lands", "land-category", key, "name"}, key),
                MessengerUtil.createMessenger(new String[]{"lands", "land-category", key, "description"}, "A land category: " + key),
                MessengerUtil.createMessenger(new String[]{"lands", "land-category", key, "lore"}, "A land category, it may has some abilities: " + key)
        );
    }

    protected static StandardLandCategory register(Namespace ns, DefinedMessenger nameMessenger, DefinedMessenger descriptionMessenger, DefinedMessenger loreMessenger) {
        StandardLandCategory landCategory = new StandardLandCategory(ns, nameMessenger, descriptionMessenger, loreMessenger);
        AuspiceAddon.get().getLandCategoryRegistry().register(landCategory);
        return landCategory;
    }



    public String getName(SupportedLanguage language) {
        return LanguageManager.getRawMessage(this.nameMessenger, language);
    }

    public String getDescription(SupportedLanguage language) {
        return LanguageManager.getRawMessage(this.descriptionMessenger, language);
    }

    public String getLore(SupportedLanguage language) {
        return LanguageManager.getRawMessage(this.loreMessenger, language);
    }


    public DefinedMessenger getNameMessenger() {
        return nameMessenger;
    }

    public DefinedMessenger getDescriptionMessenger() {
        return descriptionMessenger;
    }

    public DefinedMessenger getLoreMessenger() {
        return loreMessenger;
    }
}
