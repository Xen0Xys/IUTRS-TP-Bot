package fr.xen0xys.tpbot.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class DeadLineSlashCommand {

    private final CommandData commandData;

    public DeadLineSlashCommand(){
        SubcommandData addDeadlineCommandData = new SubcommandData("add", "Get EDT for the current day");
        addDeadlineCommandData.addOption(OptionType.CHANNEL, "channel", "Channel for deadline announcement", true);
        addDeadlineCommandData.addOption(OptionType.ROLE, "role", "Role to mention when deadline reach breakpoint", true);

        SubcommandData removeDeadlineCommandData = new SubcommandData("remove", "Get EDT for the next day");
        removeDeadlineCommandData.addOption(OptionType.STRING, "id", "Deadline ID", true);

        SubcommandData displayDeadlineCommandData = new SubcommandData("display", "get EDT for the current week");
        displayDeadlineCommandData.addOption(OptionType.STRING, "id", "Deadline ID", true);

        SubcommandData listDeadlineCommandData = new SubcommandData("list", "List all created deadlines");

        SubcommandData helpDeadlineCommandData = new SubcommandData("help", "List all created deadlines");

        this.commandData = Commands.slash("deadline", "Get EDT for defined duration").addSubcommands(
                addDeadlineCommandData, removeDeadlineCommandData, displayDeadlineCommandData, listDeadlineCommandData, helpDeadlineCommandData);
    }

    public CommandData getCommandData() {
        return commandData;
    }
}
