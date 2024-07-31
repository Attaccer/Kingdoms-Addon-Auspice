package top.mckingdom.auspice.commands.admin.land_category

import org.kingdoms.commands.CommandContext
import org.kingdoms.commands.CommandResult
import org.kingdoms.commands.KingdomsCommand
import org.kingdoms.commands.KingdomsParentCommand

class CommandAdminLandCategoryGet(parent: KingdomsParentCommand) : KingdomsCommand("get", parent) {
    override fun execute(context: CommandContext): CommandResult {
        if (!context.assertPlayer()) {

        }

        return CommandResult.FAILED
    }
}