package fr.xen0xys.tpbot.embeds.deadlines;

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
        this.setTitle("Deadlines:");
        String content;
        for(DeadLine deadLine: deadLineList){
            TextChannel channel = TPBot.getBot().getTextChannelById(deadLine.getChannelId());
            String channelMention;
            if(channel != null){
                channelMention = channel.getAsMention();
            }else{
                channelMention = "UNKNOWN";
            }
            content = String.format("""
                    **Channel:** %s
                    **Dû le:** %s
                    **Id:** %s
                    """, channelMention, Utils.getDueDateFromTimestamp(deadLine.getEndTimestamp()), deadLine.getId());
            this.addField(String.format("Nom: %s", deadLine.getName()), content, false);
        }
        this.setFooter(TPBot.getConfiguration().getEmbedFooter());
    }

}
