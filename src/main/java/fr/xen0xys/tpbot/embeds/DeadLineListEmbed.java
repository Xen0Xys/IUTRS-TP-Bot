package fr.xen0xys.tpbot.embeds;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class DeadLineListEmbed extends EmbedBuilder {

    public DeadLineListEmbed(List<DeadLine> deadLineList){
        this.setColor(Color.GREEN);
        this.setTitle("Deadlines list");
        String content = "";
        for(DeadLine deadLine: deadLineList){
            TextChannel channel = TPBot.getBot().getTextChannelById(deadLine.getChannelId());
            String channelMention;
            if(channel != null){
                channelMention = channel.getAsMention();
            }else{
                channelMention = "UNKNOWN";
            }
            content = String.format("""
                    **Name:** %s
                    **Channel:** %s
                    **Due date:** %s
                    **Id:** %s
                    """, deadLine.getName(), channelMention, Utils.getDueDateFromTimestamp(deadLine.getEndTimestamp()), deadLine.getId());
        }
        this.setDescription(content);
    }

}
