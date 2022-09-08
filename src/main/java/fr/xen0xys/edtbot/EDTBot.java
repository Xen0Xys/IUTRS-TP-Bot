package fr.xen0xys.edtbot;


import fr.xen0xys.edtbot.config.Config;
import fr.xen0xys.edtbot.events.SlashCommandListener;
import fr.xen0xys.edtbot.models.EDTParser;
import fr.xen0xys.edtbot.models.Utils;
import fr.xen0xys.edtbot.slashcommands.EDTSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EDTBot {
    public static JDA bot;
    private static Logger logger;
    private static Config config;
    private static final File DATAFOLDER = new File("TPBot");

    public static void main(String[] args){
        //Init
        InputStream stream = EDTBot.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger(EDTBot.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = new Config(DATAFOLDER, "config.yml");

        // Temp
        Utils.updateEdtFile();
        logger.info(Long.toString(new EDTParser().parseEdt().get(0).getStartTimestamp()));

        // Bot init
        try {
            bot = JDABuilder.createDefault(getConfiguration().getBotToken()).build().awaitReady();

            Guild guild = bot.getGuildById(getConfiguration().getGuildId());
            if(guild == null){
                System.exit(1);
            }
            // Slash commands
            guild.upsertCommand(new EDTSlashCommand().getCommandData()).queue();

            // Events
            bot.addEventListener(new SlashCommandListener());
        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }

        // Program loop
        Scanner scanner = new Scanner(System.in);
        String message;
        boolean running = true;
        do{
            message = scanner.nextLine();
            if(message.equals("exit")){
                running = false;
            }
        } while (running);
    }

    public static Config getConfiguration(){
        return config;
    }

    public static File getDataFolder() {
        return DATAFOLDER;
    }
}
