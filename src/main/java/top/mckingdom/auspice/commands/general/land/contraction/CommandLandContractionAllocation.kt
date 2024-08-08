package top.mckingdom.auspice.commands.general.land.contraction

import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.kingdoms.commands.*
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.LanguageManager
import org.kingdoms.utils.PlayerUtils
import top.mckingdom.auspice.configs.AuspiceLang
import top.mckingdom.auspice.data.land.Contractions
import top.mckingdom.auspice.data.land.Contractions.contractionsString
import top.mckingdom.auspice.data.land.addContraction
import top.mckingdom.auspice.data.land.hasLandContraction
import top.mckingdom.auspice.land.land_contractions.LandContraction
import top.mckingdom.auspice.land.land_contractions.StandardLandContraction
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister

class CommandLandContractionAllocation(parent: KingdomsParentCommand) : KingdomsCommand("allocation", parent) {
    override fun execute(context: CommandContext): CommandResult {
        val sender : CommandSender = context.getMessageReceiver()
        val kSender = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer())
        val lang = kSender.getLanguage() ?: LanguageManager.getDefaultLanguage()
        if (!context.isAtArg(1)) {
            AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_USAGE.sendMessage(sender)
            return CommandResult.FAILED
        }
        val land = Land.getLand(context.senderAsPlayer().location)
        val contraction : LandContraction? = contractionsString.get(lang)?.get(context.arg(0))
        val player : OfflinePlayer? = PlayerUtils.getOfflinePlayer(context.arg(1))
        if (contraction == null || player == null) {                                                             //如果两个参数不正确
            AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_USAGE.sendMessage(sender)
            return CommandResult.FAILED
        }
        if (land == null || !land.isClaimed()) {                                                                 //如果区块没被占领
            AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_NOT_CLAIMED.sendMessage(sender)
            return CommandResult.FAILED
        }
        if (land.getKingdom() != kSender.getKingdom()) {                                                         //如果是不同王国
            AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_OTHER_KINGDOM.sendMessage(sender)
            return CommandResult.FAILED
        }
        if (kSender.hasPermission(KingdomPermissionRegister.PERMISSION_MANAGE_LAND_CONTRACTIONS)   //如果分配者有"管理所有土地分配"王国权限
            || kSender.hasLandContraction(land, StandardLandContraction.MANAGE_CONTRACTIONS)       //如果分配者被分配到了"管理承包"承包项目
            || land.getClaimer() == kSender                                                        //如果土地由此人占领
//            || kSender.isAdmin()                                                                   //如果分配者是管理员
            ) {                                                                                                  //如果分配土地的人有任一权限, 即可进行分配
            val kPlayer = KingdomPlayer.getKingdomPlayer(player)
            if (kPlayer.hasLandContraction(land, contraction)) {                                                 //如果已经有了相同的分配
                AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_FAILED_ALREADY_ALLOCATION.sendMessage(sender, "contraction", contraction.getName(lang), "player", player.getName())
                return CommandResult.FAILED
            }
            land.addContraction(contraction, kPlayer)
            AuspiceLang.COMMAND_LAND_CONTRACTION_ALLOCATION_SUCCESS.sendMessage(sender, "contraction", contraction.getName(lang), "player", player.getName())
            return CommandResult.SUCCESS
        }
        println("++++++++++++++++++++++++++++出现了意外的情况!请报告插件作者!==================================")
        return CommandResult.FAILED
    }

    override fun tabComplete(context: CommandTabContext): List<String>? {
        val lang = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).getLanguage()
        if (context.isAtArg(0)) {
            contractionsString.get(lang).also {
                if (it == null) {
                    return emptyTab()
                } else {
                    return Contractions.getLandContractions(context.arg(0), lang)
                }
            }
        }
        if (context.isAtArg(1)) {
            val out = ArrayList<String>()
            val kingdom = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).getKingdom()
            return kingdom.getMembers { kp ->
                return@getMembers kp.getPlayer().getName()
            }
        }
        return emptyTab()
    }

}