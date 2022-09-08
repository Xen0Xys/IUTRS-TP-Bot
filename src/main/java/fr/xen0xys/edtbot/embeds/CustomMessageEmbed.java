package fr.xen0xys.edtbot.embeds;

import fr.xen0xys.edtbot.EDTBot;
import fr.xen0xys.edtbot.models.StatusColor;
import net.dv8tion.jda.api.EmbedBuilder;

public class CustomMessageEmbed extends EmbedBuilder {

    public CustomMessageEmbed(StatusColor statusColor, String content) {
        this.setColor(statusColor.getColor());
        this.setAuthor(content, null, EDTBot.getConfiguration().getIconUrl());
    }
}
