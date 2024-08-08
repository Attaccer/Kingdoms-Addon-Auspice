package top.mckingdom.auspice.configs;

import org.kingdoms.locale.LanguageEntry;
import org.kingdoms.locale.messenger.DefinedMessenger;

public enum AuspiceLang implements DefinedMessenger {
    COMMAND_ADMIN_LAND_CATEGORY_GET_SUCCESS("{$s}The land category of land %location% is %category%.", 1, 2, 4),  //TODO placeholder of land
    COMMAND_ADMIN_LAND_CATEGORY_SET_SUCCESS("{$s}The land category of land %location% has been changed from %old-category% to %new-category%.", 1, 2, 4),


    COMMAND_LAND_ALIASES("landControl land terra", 1, 2),
    COMMAND_LAND_DESCRIPTION("{$s}Control the anything of a land", 1, 2),


    COMMAND_LAND_CATEGORY_SET_SUCCESS("{$s}Successfully set the land category to %new-category%", 1, 2, 3, 4),
    COMMAND_LAND_CATEGORY_GET_SUCCESS("{$s}The land category of land %location% is %category%", 1, 2, 3, 4),
    COMMAND_LAND_CATEGORY_GET_FAILED_OTHER_KINGDOM("{$e}You can't see the category of other kingdom's land!", 1, 2, 3, 4),
    COMMAND_LAND_CATEGORY_GET_FAILED_NOT_CLAIMED("{$e}You can't see the category in not claimed land!", 1, 2, 3, 4),


    COMMAND_LAND_CONTRACTION_GET_ALIASES("get see", 1, 2, 3, 4, 5),
    COMMAND_LAND_CONTRACTION_GET_SUCCESS_HEAD("{$p}Land contractions:", 1, 2, 3, 4, 5),
    COMMAND_LAND_CONTRACTION_GET_SUCCESS_BODY(
            "{$s}%contraction%: " +
            "\n&7%players%",
            1, 2, 3, 4, 5
    ),
    COMMAND_LAND_CONTRACTION_GET_SUCCESS_END("{$p}+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+", 1, 2, 3, 4, 5),
    COMMAND_LAND_CONTRACTION_GET_FAILED_NOT_CLAIMED("{$e}This land is not claimed!", 1, 2, 3, 4, 5),
    COMMAND_LAND_CONTRACTION_GET_FAILED_OTHER_KINGDOM("{$e}You can not see the contractions in other kingdom's land!", 1, 2, 3, 4, 5),

    COMMAND_LAND_CONTRACTION_ALLOCATION_ALIASES("set allocation", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_DESCRIPTION("{$s}Allocation the contraction to a player.", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_USAGE("{$usage}landContraction allocation <contraction> <player>", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_SUCCESS("{$p}Success to allocation contraction {$s}%contraction% {$p}for player {$s}%player%.", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_ALREADY_ALLOCATION("{$e}Already allocation contraction {$p}%contraction% {$e}for player {$p}%player%{$e}!", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_OTHER_KINGDOM("{$e}You can not allocation contractions for other kingdom's land!", 1, 2, 3, 4),
    COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_NOT_CLAIMED("{$e}This land isn't claimed!", 1, 2, 3, 4),



    COMMAND_TRANSFER_MEMBER_DESCRIPTION("{$s}Transfer members of your kingdom to another kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_USAGE("{$usage}transferMember <YourKingdomMember> <Kingdom>", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_KINGDOM("{$e}You cannot transfer members of other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_OTHER_NATION("{$e}You cannot transfer members of other nation.", 1, 3),
    COMMAND_TRANSFER_MEMBER_FAILED_RANK_PRIORITY("{$e}You cannot transfer members with higher rank priority than yourself.", 1, 3),
    COMMAND_TRANSFER_MEMBER_REQUEST_SUCCESS("{$s}Successfully sent a request to transfer members to the other kingdom.", 1, 3),
    COMMAND_TRANSFER_MEMBER_DIRECT_SUCCESS("{$s}Successfully transferred member %player% to the other kingdom %kingdom%.", 1, 3),

    LANDS_ELYTRA_PROTECTION("{$e}You can't fly with elytra in this kingdom's land", 1),


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
