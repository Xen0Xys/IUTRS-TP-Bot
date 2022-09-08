package fr.xen0xys.edtbot.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class DeadLineSlashCommand {

    private final CommandData commandData;

    public DeadLineSlashCommand(){
        SubcommandData addDeadlineCommandData = new SubcommandData("add", "Get EDT for the current day");
        addDeadlineCommandData.addOption(OptionType.CHANNEL, "channel", "Channel for deadline announcement", true);

        SubcommandData removeDeadlineCommandData = new SubcommandData("remove", "Get EDT for the next day");
        removeDeadlineCommandData.addOption(OptionType.STRING, "id", "Deadline ID", true);

        SubcommandData displayDeadlineCommandData = new SubcommandData("display", "get EDT for the current week");
        displayDeadlineCommandData.addOption(OptionType.STRING, "id", "Deadline ID", true);

        this.commandData = Commands.slash("deadline", "Get EDT for defined duration").addSubcommands(
                addDeadlineCommandData, removeDeadlineCommandData, displayDeadlineCommandData);
    }

    public CommandData getCommandData() {
        return commandData;
    }
}
