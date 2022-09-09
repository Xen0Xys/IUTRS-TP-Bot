package fr.xen0xys.tpbot.slashcommands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class EDTSlashCommand {

    private final CommandData commandData;

    public EDTSlashCommand(){
        SubcommandData edtDayCommandData = new SubcommandData("day", "Get EDT for the current day");

        SubcommandData edtNextDayCommandData = new SubcommandData("next_day", "Get EDT for the next day");

        SubcommandData edtWeekCommandData = new SubcommandData("week", "get EDT for the current week");

        SubcommandData edtNextWeekCommandData = new SubcommandData("next_week", "get EDT for the current week");

        SubcommandData edtNextCommandData = new SubcommandData("next", "get next course on EDT");

        this.commandData = Commands.slash("edt", "Get EDT for defined duration").addSubcommands(
                edtDayCommandData, edtNextDayCommandData, edtWeekCommandData, edtNextWeekCommandData, edtNextCommandData);
    }

    public CommandData getCommandData() {
        return commandData;
    }
}
