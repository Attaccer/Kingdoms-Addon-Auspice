package top.mckingdom.auspice.commands.general.land.contraction

import org.kingdoms.commands.KingdomsParentCommand

class CommandLandContraction(parent: KingdomsParentCommand) : KingdomsParentCommand("contraction", parent){
    init {
        CommandLandContractionGet(this)
    }
}