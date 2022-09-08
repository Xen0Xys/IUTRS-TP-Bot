package fr.xen0xys.edtbot.events;

import fr.xen0xys.edtbot.commands.DeadlineCommands;
import fr.xen0xys.edtbot.embeds.CustomMessageEmbed;
import fr.xen0xys.edtbot.models.StatusColor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        switch (e.getName()){
            case "edt":
                switch (e.getSubcommandName()) {
                    case "week" -> this.weekCommand(e);
                    default -> this.sendUnknown(e, true);
                }
                break;
            case "deadline":
                switch (e.getSubcommandName()) {
                    case "add" -> DeadlineCommands.addDeadlineCommand(e);
                    case "remove" -> DeadlineCommands.removeDeadlineCommand(e);
                    case "display" -> DeadlineCommands.displayDeadlineCommand(e);
                    default -> this.sendUnknown(e, true);
                }
                break;
            default:
                this.sendUnknown(e, false);
        }
    }

    private void sendUnknown(SlashCommandInteractionEvent e, boolean isSubCommand){
        String subOrNot = isSubCommand ? "subcommand" : "command";
        e.deferReply(true).addEmbeds(new CustomMessageEmbed(StatusColor.Error, String.format("The %s '%s' is unknown, please contact an admin!", subOrNot, e.getName())).build()).queue();
    }

    private void weekCommand(SlashCommandInteractionEvent e){

    }
}
