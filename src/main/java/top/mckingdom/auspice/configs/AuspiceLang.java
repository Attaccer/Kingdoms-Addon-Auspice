package top.mckingdom.auspice.configs;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum AuspiceLang implements DefinedMessenger {
    COMMAND_ADMIN_LAND_CATEGORY_GET_SUCCESS("{$s}The land category of land %land% is %category%.", 1, 2, 4),  //TODO placeholder of land
    COMMAND_ADMIN_LAND_CATEGORY_SET_SUCCESS("{$s}The land category of land %land% has been changed from %old-category% to %new-category%.", 1, 2, 4),
    COMMAND_LAND_CATEGORY_SET_SUCCESS("{$s}Successfully set the land category to %new-category%", 1, 3),
    COMMAND_LAND_CATEGORY_GET_SUCCESS("{$s}The land category of land %land% is %category%"),


    COMMAND_TRANSFER_MEMBER_DESCRIPTION("{$s}Transfer members of your kingdom to another kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_USAGE("{$usage}transferMember <YourKingdomMember> <Kingdom>", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_KINGDOM("{$e}You cannot transfer members of other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_NATION("{$e}You cannot transfer members of other nation.", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY("{$e}You cannot transfer members with higher rank priority than yourself.", 1, 3),
    COMMAND_TRANSFER_MEMBER_REQUEST_SUCCESS("{$s}Successfully sent a request to transfer members to the other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_DIRECT_SUCCESS("{$s}Successfully transferred member %player% to the other kingdom %kingdom%.", 1, 3),


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
