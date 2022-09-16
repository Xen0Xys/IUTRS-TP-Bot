package fr.xen0xys.tpbot.models;

import fr.xen0xys.xen0lib.utils.ConfigurationReader;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class Config extends ConfigurationReader {
    public Config(File dataFolder, String fileName) {
        super(dataFolder, fileName);
    }

    // Bot
    public String getBotToken(){
        return this.getConfiguration().getString("bot.token");
    }
    public long getGuildId(){
        return this.getConfiguration().getLong("bot.guildId");
    }
    public String getIconUrl(){
        return this.getConfiguration().getString("bot.iconUrl");
    }
    public String getEmbedFooter() {
        return this.getConfiguration().getString("bot.embedFooter");
    }
    public boolean isBotActivityEnabled(){
        return this.getConfiguration().getBoolean("bot.activity.enable");
    }
    public String getBotActivityType() {
        return this.getConfiguration().getString("bot.activity.type");
    }
    public String getBotActivityText() {
        return this.getConfiguration().getString("bot.activity.text");
    }
    public String getBotActivityUrl() {
        return this.getConfiguration().getString("bot.activity.url");
    }

    // SQL
    public boolean isMySQLEnabled(){
        return this.getConfiguration().getBoolean("mysql.enable");
    }
    public HashMap<String, Object> getDatabaseInfos(){
        HashMap<String, Object> infos = new HashMap<>();
        YamlConfiguration config = this.getConfiguration();
        infos.put("host", config.getString("mysql.host"));
        infos.put("port", config.getInt("mysql.port"));
        infos.put("user", config.getString("mysql.user"));
        infos.put("password", config.getString("mysql.password"));
        infos.put("database", config.getString("mysql.database"));
        return infos;
    }

    // Modules
    public boolean isModuleEnabled(String moduleName){
        return this.getConfiguration().getBoolean(String.format("modules.%s.enable", moduleName));
    }
    public Object getModuleValue(String moduleName, String value){
        return this.getConfiguration().get(String.format("modules.%s.%s", moduleName, value));
    }
    public int getExpiryDelay(){
        return (Integer) this.getModuleValue("deadlines", "expiryDelay");
    }
    public String getTimetableURL(){
        return (String) this.getModuleValue("timetable", "url");
    }





}
