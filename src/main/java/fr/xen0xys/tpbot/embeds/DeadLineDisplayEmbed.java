package fr.xen0xys.tpbot.embeds;

import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class DeadLineDisplayEmbed extends EmbedBuilder {

    public DeadLineDisplayEmbed(DeadLine deadLine){
        this.setColor(Color.GREEN);
        this.setTitle(deadLine.getName());
        this.setDescription("**Content:**\n" + deadLine.getContent() + String.format("\n\n**Due**: %s", Utils.getDueDateFromTimestamp(deadLine.getEndTimestamp())));
        this.addField("Next announcement:", deadLine.getDeadlineStatus().toString(), false);
        this.setFooter(deadLine.getId());
    }

}
