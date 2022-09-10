package fr.xen0xys.tpbot.embeds.deadlines;

import fr.xen0xys.tpbot.models.Utils;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class DeadLineDisplayEmbed extends EmbedBuilder {

    public DeadLineDisplayEmbed(DeadLine deadLine){
        this.setColor(Color.GREEN);
        this.setTitle(deadLine.getName());
        this.setDescription("**Description:**\n" + deadLine.getContent() + String.format("\n\n**Dû le**: %s", Utils.getDueDateFromTimestamp(deadLine.getEndTimestamp())));
        this.addField("Prochaine annonce:", deadLine.getDeadlineStatus().getDisplay(), false);
        this.setFooter(deadLine.getId());
    }

}
