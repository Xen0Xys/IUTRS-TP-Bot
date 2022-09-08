package fr.xen0xys.edtbot.models;

import fr.xen0xys.xen0lib.utils.ConfigurationReader;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class Config extends ConfigurationReader {
    public Config(File dataFolder, String fileName) {
        super(dataFolder, fileName);
    }

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

    public String getBotToken(){
        return this.getConfiguration().getString("bot.token");
    }

    public long getGuildId(){
        return this.getConfiguration().getLong("bot.guildId");
    }

    public String getEdtUrl(){
        return this.getConfiguration().getString("edt.url");
    }

    public String getIconUrl(){
        return this.getConfiguration().getString("bot.iconUrl");
    }
}