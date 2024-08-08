package top.mckingdom.auspice.commands.general.land;

import org.kingdoms.commands.KingdomsParentCommand;
import top.mckingdom.auspice.commands.general.land.category.CommandLandCategory;
import top.mckingdom.auspice.commands.general.land.contraction.CommandLandContraction;

public class CommandLand extends KingdomsParentCommand {

    private static CommandLand instance;
    public CommandLand() {
        super("landControl", true);
        new CommandLandCategory(this);
        new CommandLandContraction(this);
    }

    public static CommandLand getInstance() {
        return instance;
    }

    public static void setInstance(CommandLand instance) {
        CommandLand.instance = instance;
    }
}
