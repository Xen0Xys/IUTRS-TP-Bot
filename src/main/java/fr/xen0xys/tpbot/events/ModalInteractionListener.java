package fr.xen0xys.tpbot.events;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.xen0lib.utils.Status;
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
            String name = e.getValue("name").getAsString();
            String content = e.getValue("content").getAsString();
            long endTimestamp = Long.parseLong(e.getValue("endtimestamp").getAsString());

            // Create deadline
            DeadLine deadLine = new DeadLine(Utils.generateId(), name, content, endTimestamp, channelId, Utils.getDeadlineStatusFromTimestamp(endTimestamp));
            TPBot.getDeadLines().put(deadLine.getId(), deadLine); // Add deadline for async status check
            Status status = TPBot.getDeadLinesTable().addDeadLine(deadLine); // Add deadline to the DB

            // Check if the deadline has been added to the DB
            switch (status) {
                case Success -> e.deferReply(true).setContent("Deadline added!").queue();
                case SQLError -> e.deferReply(true).setContent("An error occurred while adding the deadline!").queue();
            }
        }
    }
}
