package fr.xen0xys.tpbot.events;

import fr.xen0xys.tpbot.commands.DeadlineCommands;
import fr.xen0xys.tpbot.embeds.CustomMessageEmbed;
import fr.xen0xys.tpbot.embeds.StatusColor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        switch (e.getName()){
            case "edt":
                break;
            case "deadline":
                switch (e.getSubcommandName()) {
                    case "add" -> DeadlineCommands.addDeadlineCommand(e);
                    case "remove" -> DeadlineCommands.removeDeadlineCommand(e);
                    case "display" -> DeadlineCommands.displayDeadlineCommand(e);
                    case "list" -> DeadlineCommands.listDeadlinesCommand(e);
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
}
