package fr.xen0xys.tpbot.embeds;

import fr.xen0xys.tpbot.TPBot;
import net.dv8tion.jda.api.EmbedBuilder;

public class CustomMessageEmbed extends EmbedBuilder {

    public CustomMessageEmbed(StatusColor statusColor, String content) {
        this.setColor(statusColor.getColor());
        this.setAuthor(content, null, TPBot.getConfiguration().getIconUrl());
        this.setFooter(TPBot.getConfiguration().getEmbedFooter());
    }
}
