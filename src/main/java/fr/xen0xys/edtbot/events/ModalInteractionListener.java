package fr.xen0xys.edtbot.events;

import fr.xen0xys.edtbot.EDTBot;
import fr.xen0xys.edtbot.models.Utils;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ModalInteractionListener extends ListenerAdapter {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if(e.getModalId().startsWith("deadline-")){
            long channelId = Long.parseLong(e.getModalId().split("-")[1]);
            String name = e.getValue("name").getAsString();
            String content = e.getValue("content").getAsString();
            long endTimestamp = Long.parseLong(e.getValue("endtimestamp").getAsString());
            e.getInteraction().deferReply(true).setContent("Modal interaction").queue();
            EDTBot.getDeadLinesTable().addDeadLine(name, content, endTimestamp, channelId, Utils.getDeadlineStatusFromTimestamp(endTimestamp));
        }
    }
}
