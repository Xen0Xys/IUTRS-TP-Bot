package fr.xen0xys.tpbot.models.deadline;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.embeds.deadlines.DeadLineDisplayEmbed;
import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.xen0lib.utils.Status;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.HashMap;

public class DeadlineStatusRunnable extends Thread {

    private boolean isRunning = false;

    @Override
    public void run() {
        TPBot.getLogger().info("Starting async deadline status updater");
        this.isRunning = true;
        while (isRunning){
            try {
                Thread.sleep(1000);
                // Async
                for(DeadLine deadLine: new HashMap<>(TPBot.getDeadLines()).values()){
                    if(deadLine.getDeadlineStatus() != DeadlineStatus.PASSED){
                        DeadlineStatus newDeadlineStatus;
                        if((newDeadlineStatus = Utils.getDeadlineStatusFromTimestamp(deadLine.getEndTimestamp())) != deadLine.getDeadlineStatus()){
                            this.updatingDeadline(deadLine, newDeadlineStatus);
                        }
                    }else{
                        TPBot.getDeadLines().remove(deadLine.getId());
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        TPBot.getLogger().info("Stopped async deadline status updater");
    }

    public void shutdown(){
        this.isRunning = false;
    }

    private void updatingDeadline(DeadLine deadLine, DeadlineStatus newDeadlineStatus){
        TPBot.getLogger().info(String.format("Updating deadline status for deadline: %s with ID: %s...", deadLine.getName(), deadLine.getId()));
        TPBot.getLogger().info(String.format("Deadline change from %s to %s", deadLine.getDeadlineStatus(), newDeadlineStatus));
        // Send notification in targeted channel
        TextChannel channel = TPBot.getBot().getTextChannelById(deadLine.getChannelId());
        if(channel != null){
            // Create a copy of the local deadline in case of crash during the update
            DeadLine temporaryDeadline = new DeadLine(deadLine);
            temporaryDeadline.setDeadlineStatus(newDeadlineStatus);

            // Update deadline display
            this.updateDeadlineDisplay(deadLine, temporaryDeadline, channel);

            // Update status
            if(newDeadlineStatus != DeadlineStatus.PASSED) {
                // If deadline is not passed, update status locally
                deadLine.setDeadlineStatus(newDeadlineStatus);
            }else{
                // If deadline is passed, remove them locally
                TPBot.getDeadLines().remove(deadLine.getId());
            }
            // In two cases, try updating status to database (not removing passed deadlines ATM)
            if(TPBot.getDeadLinesTable().updateDeadlineStatus(deadLine.getId(), newDeadlineStatus) != Status.Success){
                // If error when pushing status change to database
                TPBot.getLogger().severe("An error occurred while updating deadline status in the DB");
            }else{
                TPBot.getLogger().info("Deadline has been updated!");
            }
        }else{
            TPBot.getLogger().severe("Channel not found for deadline");
        }
    }

    private void updateDeadlineDisplay(DeadLine deadline, DeadLine temporaryDeadline, TextChannel channel){
        if(((System.currentTimeMillis() / 1000) < deadline.getEndTimestamp() + (TPBot.getConfiguration().getExpiryDelay() * 60L))){
            Role role = (TPBot.getBot().getRoleById(deadline.getMentionRoleId()));
            if(role != null){
                channel.sendMessageEmbeds(new DeadLineDisplayEmbed(temporaryDeadline).build()).setContent(role.getAsMention()).complete();
            }else{
                channel.sendMessageEmbeds(new DeadLineDisplayEmbed(temporaryDeadline).build()).setContent("@everyone").complete();
            }
        }else{
            channel.sendMessageEmbeds(new DeadLineDisplayEmbed(temporaryDeadline).build()).complete();
        }
    }
}
