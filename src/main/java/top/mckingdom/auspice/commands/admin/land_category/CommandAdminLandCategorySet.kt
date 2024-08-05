package top.mckingdom.auspice.commands.admin.land_category

import org.bukkit.permissions.PermissionDefault
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.locale.LanguageManager
import org.kingdoms.locale.SupportedLanguage
import top.mckingdom.auspice.AuspiceAddon
import top.mckingdom.auspice.configs.AuspiceLang
import top.mckingdom.auspice.data.land.setCategory
import top.mckingdom.auspice.land.land_categories.LandCategory
import top.mckingdom.auspice.utils.LandCategoriesUtil


class CommandAdminLandCategorySet(parent: KingdomsParentCommand) : KingdomsCommand("set", parent, PermissionDefault.OP) {


    override fun execute(context: CommandContext): CommandResult {


        val lang : SupportedLanguage = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()
        val category : LandCategory? = categoriesString.get(lang)?.get(context.arg(0))

        if (category == null) {

        } else {
            val land = Land.getLand(context.senderAsPlayer().location)
            land?.setCategory(category)
            AuspiceLang.COMMAND_ADMIN_LAND_CATEGORY_SET_SUCCESS.sendMessage(context.messageReceiver,
                "chunk-location", land.location.x.toString() + " " + land.location.z
                )
        }

       return CommandResult.SUCCESS

    }

    override fun tabComplete(context: CommandTabContext): MutableList<String> {
        val lang : SupportedLanguage = context.kingdomPlayer.language ?: LanguageManager.getDefaultLanguage()
        AuspiceAddon.get().landCategoryRegistry.registry.keys.forEach { ns -> categoriesString.get(lang)?.put(ns.asNormalizedString(), AuspiceAddon.get().landCategoryRegistry.getRegistered(ns)) }

        if (context.isAtArg(0)) {
            return LandCategoriesUtil.getLandCategories(context.arg(0), lang)
        }
        if (context.isAtArg(1)) {
            return mutableListOf("<x>")    //TODO
        }
        if (context.isAtArg(2)) {
            return mutableListOf("<z>")    //TODO
        }
        return emptyTab()
    }


    //Language在KX onLoad 的时候加载
    //这些命令在Addon onEnable 的时候加载
    init {
        SupportedLanguage.entries.forEach { lang -> categoriesString.put(lang, HashMap()) }
    }

    companion object {
        @JvmStatic
        val categoriesString = HashMap<SupportedLanguage, HashMap<String, LandCategory>>()

    }


}