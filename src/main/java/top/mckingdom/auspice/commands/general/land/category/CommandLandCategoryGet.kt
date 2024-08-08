package top.mckingdom.auspice.commands.general.land.category

import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import top.mckingdom.auspice.configs.AuspiceLang
import top.mckingdom.auspice.data.land.getCategory
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister

class CommandLandCategoryGet(parent: KingdomsParentCommand): KingdomsCommand("get", parent) {
    override fun execute(context: CommandContext): CommandResult {
        val player = context.senderAsPlayer()
        val kp = KingdomPlayer.getKingdomPlayer(player)
        val lang = kp.language
        val land = Land.getLand(player.location)
        if (land == null || !land.isClaimed()) {
            AuspiceLang.COMMAND_LAND_CATEGORY_GET_FAILED_NOT_CLAIMED.sendMessage(player)
            return CommandResult.FAILED
        }
        val x = land.location.x
        val z = land.location.z
        if (land.getKingdom() != kp.getKingdom()) {
            AuspiceLang.COMMAND_LAND_CATEGORY_GET_FAILED_OTHER_KINGDOM.sendMessage(player)
            return CommandResult.FAILED
        }

        if (kp.hasPermission(KingdomPermissionRegister.PERMISSION_MANAGE_LAND_CATEGORIES)
            || kp.isAdmin()
            ) {
            AuspiceLang.COMMAND_LAND_CATEGORY_GET_SUCCESS.sendMessage(player, "location", "$x $z", "category", land.getCategory()!!.getName(lang))

            return CommandResult.SUCCESS
        }

        println("++++++++++++++++++++++出现了意外的情况! 请联系插件作者(Attaccer)============================")
        return CommandResult.FAILED
    }
}