package fr.xen0xys.edtbot.events;

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
                switch (e.getSubcommandName()){
                    case "week":
                        this.weekCommand(e);
                    default:
                        e.deferReply(true).addEmbeds(new CustomMessageEmbed(StatusColor.Error, String.format("The subcommand '%s' is unknown, please contact an admin!", e.getSubcommandName())).build()).queue();
                }
            default:
                e.deferReply(true).addEmbeds(new CustomMessageEmbed(StatusColor.Error, String.format("The command '%s' is unknown, please contact an admin!", e.getName())).build()).queue();
        }
    }

    private void weekCommand(SlashCommandInteractionEvent e){

    }
}
