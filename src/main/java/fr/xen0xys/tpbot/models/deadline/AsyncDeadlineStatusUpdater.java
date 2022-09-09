package fr.xen0xys.tpbot.models.deadline;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.embeds.DeadLineDisplayEmbed;
import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.xen0lib.utils.Status;
import net.dv8tion.jda.api.entities.TextChannel;

public class AsyncDeadlineStatusUpdater extends Thread {

    private boolean isRunning = false;

    public AsyncDeadlineStatusUpdater(){

    }

    @Override
    public void run() {
        TPBot.getLogger().info("Starting async deadline status updater");
        this.isRunning = true;
        while (isRunning){
            try {
                Thread.sleep(1000);
                // Async
                for(DeadLine deadLine: TPBot.getDeadLines().values()){
                    if(deadLine.getDeadlineStatus() != DeadlineStatus.ENDED){
                        DeadlineStatus deadlineStatus;
                        if((deadlineStatus = Utils.getDeadlineStatusFromTimestamp(deadLine.getEndTimestamp())) != deadLine.getDeadlineStatus()){
                            // Send notification in targeted channel
                            TextChannel channel = TPBot.getBot().getTextChannelById(deadLine.getChannelId());
                            if(channel != null){
                                // Create a copy of deadline in case of crash during the update
                                DeadLine temporaryDeadline = new DeadLine(deadLine);
                                temporaryDeadline.setDeadlineStatus(deadlineStatus);
                                channel.sendMessageEmbeds(new DeadLineDisplayEmbed(temporaryDeadline).build()).complete();
                                // Update status
                                deadLine.setDeadlineStatus(deadlineStatus);
                                if(TPBot.getDeadLinesTable().updateDeadlineStatus(deadLine.getId(), deadlineStatus) != Status.Success){
                                    TPBot.getLogger().severe("An error occurred while updating deadline status in the DB");
                                }
                            }else{
                                TPBot.getLogger().severe("Channel not found for deadline " + deadLine.getId());
                            }
                        }
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
}
