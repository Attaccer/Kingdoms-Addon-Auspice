package top.mckingdom.auspice.configs;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum AuspiceAddonLang implements DefinedMessenger {
    PERMISSION_TRANSFER_MEMBERS("You don't have permission to transfer members."),



    ;

    private final LanguageEntry languageEntry;
    private final String defaultValue;

    AuspiceAddonLang(String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.languageEntry = DefinedMessenger.getEntry("auspice-addon", this, grouped);
    }

    @Override
    public LanguageEntry getLanguageEntry() {
        return languageEntry;
    }

    @Override
    public String getDefaultValue() {
        return null;
    }
}
