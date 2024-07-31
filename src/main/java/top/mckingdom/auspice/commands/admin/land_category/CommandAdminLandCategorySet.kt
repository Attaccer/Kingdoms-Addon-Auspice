package top.mckingdom.auspice.commands.admin.land_category

import org.bukkit.permissions.PermissionDefault
import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.locale.LanguageManager
import org.kingdoms.locale.SupportedLanguage
import top.mckingdom.auspice.AuspiceAddon
import top.mckingdom.auspice.land_categories.LandCategory
import top.mckingdom.auspice.land_categories.StandardLandCategory
import kotlin.collections.HashMap


class CommandAdminLandCategorySet(parent: KingdomsParentCommand) : KingdomsCommand("set", parent, PermissionDefault.OP) {


    override fun execute(context: CommandContext): CommandResult {
        val lang : SupportedLanguage = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()
        AuspiceAddon.get().landCategoryRegistry.getRegistry().values.forEach {category -> categoriesString.getValue ((category as StandardLandCategory).getName(lang)) ;   }

        context.senderAsPlayer().location
    }


    companion object {
        var categoriesString : Map<String, LandCategory> = HashMap<String, LandCategory>()

    }

}