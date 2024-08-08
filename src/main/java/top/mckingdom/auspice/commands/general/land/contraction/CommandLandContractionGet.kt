package top.mckingdom.auspice.commands.general.land.contraction

import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand
import org.kingdoms.constants.land.Land
import org.kingdoms.constants.land.location.SimpleChunkLocation
import org.kingdoms.constants.player.KingdomPlayer
import org.kingdoms.locale.LanguageManager
import org.kingdoms.locale.SupportedLanguage
import org.kingdoms.locale.compiler.builders.MessageObjectLinker
import top.mckingdom.auspice.configs.AuspiceLang
import top.mckingdom.auspice.data.land.getContractions

class CommandLandContractionGet(parent: KingdomsParentCommand): KingdomsCommand("get", parent) {

    override fun execute(context: CommandContext): CommandResult {
        val location = SimpleChunkLocation.of(context.senderAsPlayer().location)
        if (show(context, location)) {
            return CommandResult.SUCCESS
        } else {
            return CommandResult.FAILED
        }
    }

    companion object{
        @JvmStatic
        fun show(context: CommandContext, location: SimpleChunkLocation) : Boolean {
            val sender = context.getMessageReceiver()
            val lang : SupportedLanguage = KingdomPlayer.getKingdomPlayer(context.senderAsPlayer()).getLanguage() ?: LanguageManager.getDefaultLanguage()

            val land = Land.getLand(location)
            val x = location.x
            val z = location.z
            if (land == null || !land.isClaimed()) {
                AuspiceLang.COMMAND_LAND_CONTRACTION_GET_FAILED_NOT_CLAIMED.sendMessage(sender, "location", "$x $z")
                return false
            }
            AuspiceLang.COMMAND_LAND_CONTRACTION_GET_SUCCESS_HEAD.sendMessage(sender, "location", "$x $z")
            land.getContractions()!!.forEach { entry ->
                val linker = MessageObjectLinker()
                val name = entry.key.getName(lang)
                entry.value.forEach { uuid ->
                    linker.add(KingdomPlayer.getKingdomPlayer(uuid).getPlayer().getName())
                }
                AuspiceLang.COMMAND_LAND_CONTRACTION_GET_SUCCESS_BODY.sendMessage(sender, "contraction-name", name, "players", linker.build(context.messageContext))
            }
            AuspiceLang.COMMAND_LAND_CONTRACTION_GET_SUCCESS_END.sendMessage(sender, "location", "$x $z")
            return true
        }
    }

}