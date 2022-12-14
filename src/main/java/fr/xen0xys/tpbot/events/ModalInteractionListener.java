package fr.xen0xys.tpbot.events;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.embeds.CustomMessageEmbed;
import fr.xen0xys.tpbot.embeds.StatusColor;
import fr.xen0xys.tpbot.embeds.deadlines.DeadLineDisplayEmbed;
import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.xen0lib.utils.Status;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ModalInteractionListener extends ListenerAdapter {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if(e.getModalId().startsWith("deadline-")){
            // Parse deadline data
            long channelId = Long.parseLong(e.getModalId().split("-")[1]);
            long mentionRoleId = Long.parseLong(e.getModalId().split("-")[2]);
            String name = e.getValue("name").getAsString();
            String content = e.getValue("content").getAsString();
            long endTimestamp = Long.parseLong(e.getValue("endtimestamp").getAsString());

            // Check channel id
            TextChannel channel = TPBot.getBot().getTextChannelById(channelId);
            Role role = TPBot.getBot().getRoleById(mentionRoleId);
            if(channel != null){
                if(role != null){
                    // Create deadline
                    String deadlineId = Utils.generateId();
                    DeadLine deadLine = new DeadLine(deadlineId, name, content, endTimestamp, channelId, mentionRoleId, Utils.getDeadlineStatusFromTimestamp(endTimestamp));
                    TPBot.getDeadLines().put(deadLine.getId(), deadLine); // Add deadline for async status check
                    Status status = TPBot.getDeadLinesTable().addDeadLine(deadLine); // Add deadline to the DB

                    // Check if the deadline has been added to the DB
                    switch (status) {
                        case Success -> {
                            channel.sendMessageEmbeds(new DeadLineDisplayEmbed(deadLine).build()).setContent(role.getAsMention()).queue();
                            e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Ok, String.format("Deadline created with id: %s", deadlineId)).build()).queue();
                            TPBot.getLogger().info(String.format("Deadline created with id: %s", deadlineId));
                        }
                        case SQLError ->{
                            e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Error, "An error occurred while adding the deadline!").build()).queue();
                            TPBot.getLogger().info("Error with database when creating deadline, aborting!");
                        }
                    }
                }else{
                    e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Error, "Invalid role!").build()).queue();
                    TPBot.getLogger().warning("Given role not found, aborting deadline creation!");
                }
            }else{
                e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Error, "Invalid channel!").build()).queue();
                TPBot.getLogger().warning("Given channel not found, aborting deadline creation!");
            }
        }
    }
}
