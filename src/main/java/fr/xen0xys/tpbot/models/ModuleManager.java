package fr.xen0xys.tpbot.models;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.deadline.AsyncDeadlineStatusUpdater;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatus;

public class ModuleManager {

    private final Config config;

    private AsyncDeadlineStatusUpdater asyncDeadlineStatusUpdater;

    public ModuleManager(Config config){
        this.config = config;
    }

    public void loadModules(){
        if(this.config.isModuleEnabled("deadlines")){
            TPBot.getLogger().info("[MODULE MANAGER] Loading deadlines module...");
            this.loadDeadlines();
            TPBot.getLogger().info("[MODULE MANAGER] Deadlines module loaded!");
        }
    }

    public void unloadModules(){
        if(this.config.isModuleEnabled("deadlines")){
            TPBot.getLogger().info("[MODULE MANAGER] Unloading deadlines module...");
            this.unloadDeadlines();
            TPBot.getLogger().info("[MODULE MANAGER] Deadlines module unloaded!");
        }
    }

    private void loadDeadlines(){
        TPBot.getDeadLines().clear();
        for(DeadLine deadLine : TPBot.getDeadLinesTable().getDeadLines()){
            if(deadLine.getDeadlineStatus() != DeadlineStatus.PASSED){
                TPBot.getDeadLines().put(deadLine.getId(), deadLine);
                TPBot.getLogger().info(String.format("[DEADLINES] Loaded deadline: %s with ID: %s", deadLine.getName(), deadLine.getId()));
            }
        }
        this.asyncDeadlineStatusUpdater = new AsyncDeadlineStatusUpdater();
        this.asyncDeadlineStatusUpdater.start();
    }
    private void unloadDeadlines(){
        TPBot.getDeadLines().clear();
        asyncDeadlineStatusUpdater.shutdown();
    }

}
