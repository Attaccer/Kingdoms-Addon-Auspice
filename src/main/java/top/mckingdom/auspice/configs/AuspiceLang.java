package top.mckingdom.auspice.configs;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum AuspiceLang implements DefinedMessenger {
    COMMAND_TRANSFER_MEMBER_DESCRIPTION("{$s}Transfer members of your kingdom to another kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_USAGE("{$usage}transferMember <YourKingdomMember> <Kingdom>", 1, 3),
    COMMAND_TRANSFER_MEMBER_OTHER_KINGDOM("{$e}You cannot transfer members of other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_RANK_PRIORITY("{$e}You cannot transfer members with higher rank priority than yourself.", 1, 3),
    COMMAND_TRANSFER_MEMBER_REQUEST_SUCCESS("{$s}Successfully sent a request to transfer members to the other kingdom.", 1, 3, 4),
    COMMAND_TRANSFER_MEMBER_DIRECT_SUCCESS("{$s}Successfully transferred member %player% to the other kingdom %kingdom%.", 1, 3, 4),

    COMMAND_LAND_CATEGORY_SET_SUCCESS("{$s}Successfully set the land category to %category%", 1, 3, 4)

    ;

    private final LanguageEntry languageEntry;
    private final String defaultValue;

    AuspiceLang(String defaultValue, int... grouped) {
        this.defaultValue = defaultValue;
        this.languageEntry = DefinedMessenger.getEntry(null, this, grouped);
    }

    @Override
    public LanguageEntry getLanguageEntry() {
        return languageEntry;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
