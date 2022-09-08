package fr.xen0xys.edtbot.config;

import fr.xen0xys.xen0lib.utils.ConfigurationReader;

import java.io.File;

public class Config extends ConfigurationReader {
    public Config(File dataFolder, String fileName) {
        super(dataFolder, fileName);
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
