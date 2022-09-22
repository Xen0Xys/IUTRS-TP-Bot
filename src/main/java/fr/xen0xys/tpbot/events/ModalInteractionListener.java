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
        if(e.getModalId().startsWith("deadline")){
            // Parse deadline data
            long channelId = Long.parseLong(e.getModalId().split("-")[1]);
            long mentionRoleId = Long.parseLong(e.getModalId().split("-")[2]);
            String deadlineId = e.getModalId().split("-")[3];
            String name = e.getValue("name").getAsString();
            String content = e.getValue("content").getAsString();
            long endTimestamp = Long.parseLong(e.getValue("endtimestamp").getAsString());

            // Get channel and mentionRole from their ids
            TextChannel channel = TPBot.getBot().getTextChannelById(channelId);
            Role mentionRole = TPBot.getBot().getRoleById(mentionRoleId);

            // Check if the deadline to be created or updated
            boolean update;
            update = !deadlineId.equals("0");

            // Create or update deadline
            DeadLine deadline = this.createOrUpdateDeadline(deadlineId, channel, mentionRole, name, content, endTimestamp, update);

            // Check result and respond to user
            if(deadline != null){
                channel.sendMessageEmbeds(new DeadLineDisplayEmbed(deadline).build()).setContent(mentionRole.getAsMention()).queue();
                if(update){
                    e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Ok, String.format("Deadline updated with id: %s", deadline.getId())).build()).queue();
                    TPBot.getLogger().info(String.format("Deadline updated with id: %s", deadline.getId()));
                }else{
                    e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Ok, String.format("Deadline created with id: %s", deadline.getId())).build()).queue();
                    TPBot.getLogger().info(String.format("Deadline created with id: %s", deadline.getId()));
                }
            }else{
                e.deferReply(true).setEmbeds(new CustomMessageEmbed(StatusColor.Error, "An error occurred while adding the deadline!").build()).queue();
                TPBot.getLogger().info("Error with database when creating deadline, aborting!");
            }
        }
    }

    /**
     * Create or update a deadline
     * @param deadlineId The deadline id
     * @param channel The channel where the deadline will be displayed
     * @param mentionRole The role to mention when the deadline is displayed
     * @param name The name of the deadline
     * @param content The content of the deadline
     * @param endTimestamp The end timestamp of the deadline
     * @param update If the deadline should be updated or created
     * @return The deadline created or updated
     */
    private DeadLine createOrUpdateDeadline(String deadlineId, TextChannel channel, Role mentionRole, String name, String content, long endTimestamp, boolean update){
        if(channel != null) {
            if (mentionRole != null) {
                DeadLine deadLine;
                if(update){
                    // Update deadline
                    deadLine = TPBot.getDeadLines().get(deadlineId);
                    deadLine.setChannelId(channel.getIdLong());
                    deadLine.setMentionRoleId(mentionRole.getIdLong());
                    deadLine.setName(name);
                    deadLine.setContent(content);
                    deadLine.setEndTimestamp(endTimestamp);
                    Status status = TPBot.getDeadLinesTable().updateDeadline(deadLine);
                    if(status == Status.Success){
                        return deadLine;
                    }
                }else{
                    // Create deadline
                    deadLine = new DeadLine(Utils.generateId(), name, content, endTimestamp, channel.getIdLong(), mentionRole.getIdLong(), Utils.getDeadlineStatusFromTimestamp(endTimestamp));
                    Status status = TPBot.getDeadLinesTable().addDeadLine(deadLine); // Add deadline to the DB
                    if(status == Status.Success){
                        TPBot.getDeadLines().put(deadLine.getId(), deadLine); // Add deadline for async status check
                        return deadLine;
                    }
                }
                return null;
            }
        }
        return null;
    }
}
