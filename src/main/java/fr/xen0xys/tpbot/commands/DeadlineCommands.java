package fr.xen0xys.tpbot.commands;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.embeds.DeadLineDisplayEmbed;
import fr.xen0xys.tpbot.embeds.DeadLineListEmbed;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.xen0lib.utils.Status;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.util.ArrayList;

public abstract class DeadlineCommands {

    @SuppressWarnings("ConstantConditions")
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

    @SuppressWarnings("ConstantConditions")
    public static void removeDeadlineCommand(SlashCommandInteractionEvent e){
        String deadlineId = e.getOption("id").getAsString();
        if(!deadlineId.startsWith("#")){
            deadlineId = "#" + deadlineId;
        }
        TPBot.getDeadLines().remove(deadlineId);
        Status status = TPBot.getDeadLinesTable().deleteDeadLine(deadlineId);
        switch (status) {
            case Success -> e.deferReply(true).setContent("Deadline deleted").queue();
            case SQLError -> e.deferReply(true).setContent("SQL Error").queue();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void displayDeadlineCommand(SlashCommandInteractionEvent e){
        String deadlineId = e.getOption("id").getAsString();
        if(!deadlineId.startsWith("#")){
            deadlineId = "#" + deadlineId;
        }
        DeadLine deadLine = TPBot.getDeadLines().get(deadlineId);
        e.deferReply(true).addEmbeds(new DeadLineDisplayEmbed(deadLine).build()).complete();
    }

    public static void listDeadlinesCommand(SlashCommandInteractionEvent e){
        e.deferReply(true).addEmbeds(new DeadLineListEmbed(new ArrayList<>(TPBot.getDeadLines().values())).build()).complete();
    }

}
