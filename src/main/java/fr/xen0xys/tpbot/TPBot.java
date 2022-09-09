package fr.xen0xys.tpbot;


import fr.xen0xys.tpbot.database.DeadLinesTable;
import fr.xen0xys.tpbot.events.ModalInteractionListener;
import fr.xen0xys.tpbot.events.SlashCommandListener;
import fr.xen0xys.tpbot.models.Config;
import fr.xen0xys.tpbot.models.deadline.AsyncDeadlineStatusUpdater;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.tpbot.slashcommands.DeadLineSlashCommand;
import fr.xen0xys.tpbot.slashcommands.EDTSlashCommand;
import fr.xen0xys.xen0lib.database.Database;
import fr.xen0xys.xen0lib.utils.Status;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TPBot {
    public static JDA bot;
    private static Logger logger;
    private static Config config;
    private static final File DATAFOLDER = new File("TPBot");
    private static final HashMap<String, DeadLine> deadLines = new HashMap<>();

    private static DeadLinesTable deadLinesTable;

    public static void main(String[] args){
        //Init
        InputStream stream = TPBot.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger(TPBot.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = new Config(DATAFOLDER, "config.yml");

        // Temp
        // Utils.updateEdtFile();
        // logger.info(Long.toString(new EDTParser().parseEdt().get(0).getStartTimestamp()));

        // Database init
        Database database;
        if(getConfiguration().isMySQLEnabled()){
            HashMap<String, Object> databaseInfos = getConfiguration().getDatabaseInfos();
            database = new Database(String.valueOf(databaseInfos.get("host")),
                    Integer.parseInt(String.valueOf(databaseInfos.get("port"))),
                    String.valueOf(databaseInfos.get("user")),
                    String.valueOf(databaseInfos.get("password")),
                    String.valueOf(databaseInfos.get("database")), logger);
        }else{
            database = new Database(DATAFOLDER.getPath(), "TPBot", logger);
        }
        if(database.connect() != Status.Success){
            logger.severe("Cannot connect to Database, stopping bot...");
            System.exit(1);
        }
        deadLinesTable = new DeadLinesTable("tpbot_deadlines", database);
        database.openTableAndCreateINE(deadLinesTable, "id VARCHAR(6) NOT NULL PRIMARY KEY," +
                "name VARCHAR(50)," +
                "content TEXT," +
                "endTimestamp BIGINT," +
                "channelId BIGINT," +
                "status VARCHAR(11)");

        // Deadline loading
        loadDeadLines();
        AsyncDeadlineStatusUpdater asyncDeadlineStatusUpdater = new AsyncDeadlineStatusUpdater();
        asyncDeadlineStatusUpdater.start();

        // Bot init
        try {
            bot = JDABuilder.createDefault(getConfiguration().getBotToken()).build().awaitReady();

            Guild guild = bot.getGuildById(getConfiguration().getGuildId());
            if(guild == null){
                System.exit(1);
            }
            // Slash commands
            guild.upsertCommand(new EDTSlashCommand().getCommandData()).queue();
            guild.upsertCommand(new DeadLineSlashCommand().getCommandData()).queue();

            // Events
            bot.addEventListener(new SlashCommandListener());
            bot.addEventListener(new ModalInteractionListener());
        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }

        // Program loop
        Scanner scanner = new Scanner(System.in);
        String message;
        boolean running = true;
        do{
            message = scanner.nextLine();
            if(message.equals("exit") || message.equals("stop")){
                running = false;
                asyncDeadlineStatusUpdater.shutdown();
                database.disconnect();
                bot.shutdownNow();
            }
        } while (running);
    }

    private static void loadDeadLines(){
        for(DeadLine deadLine : getDeadLinesTable().getDeadLines()){
            TPBot.deadLines.put(deadLine.getId(), deadLine);
        }
    }

    public static Config getConfiguration(){
        return config;
    }

    public static File getDataFolder() {
        return DATAFOLDER;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static DeadLinesTable getDeadLinesTable() {
        return deadLinesTable;
    }

    public static HashMap<String, DeadLine> getDeadLines() {
        return deadLines;
    }

    public static JDA getBot() {
        return bot;
    }
}
