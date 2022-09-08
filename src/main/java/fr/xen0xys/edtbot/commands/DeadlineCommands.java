package fr.xen0xys.edtbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

public abstract class DeadlineCommands {

    public static void addDeadlineCommand(SlashCommandInteractionEvent e){
        long channelId = e.getOption("channel").getAsLong();
        TextInput name = TextInput.create("name", "Name", TextInputStyle.SHORT)
                .setPlaceholder("Name of the deadline")
                .setMaxLength(50)
                .setRequired(true)
                .build();
        TextInput content = TextInput.create("content", "Content", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Content of the deadline")
                .setMaxLength(1000)
                .setRequired(true)
                .build();
        TextInput endTimestamp = TextInput.create("endtimestamp", "End Timestamp", TextInputStyle.SHORT)
                .setPlaceholder("Timestamp for the end of the deadline")
                .setMaxLength(10)
                .setRequired(true)
                .build();
        Modal modal = Modal.create(String.format("deadline-%d", channelId), "Deadline Modal")
                .addActionRows(ActionRow.of(name), ActionRow.of(content), ActionRow.of(endTimestamp))
                .build();
        e.replyModal(modal).queue();
    }

    public static void removeDeadlineCommand(SlashCommandInteractionEvent e){

    }

    public static void displayDeadlineCommand(SlashCommandInteractionEvent e){

    }

}
