package fr.xen0xys.tpbot.embeds.deadlines;

import fr.xen0xys.tpbot.TPBot;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class DeadlinesHelpEmbed extends EmbedBuilder {

    public DeadlinesHelpEmbed(){
        this.setColor(Color.ORANGE);
        this.setTitle("Deadlines help:");
        this.addField("Deadline List", "Liste toutes les deadlines", false);
        this.addField("Deadline Display", "Montre une deadline\nDemande l'ID de la deadline", false);
        this.setFooter(TPBot.getConfiguration().getEmbedFooter());
    }
}
