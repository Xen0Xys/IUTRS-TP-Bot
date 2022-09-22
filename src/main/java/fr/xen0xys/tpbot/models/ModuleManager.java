package fr.xen0xys.tpbot.models;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatus;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatusRunnable;

public class ModuleManager {

    private final Config config;

    private DeadlineStatusRunnable deadlineStatusRunnable;

    /**
     * Constructor of ModuleManager
     * @param config Config object, used for module management
     */
    public ModuleManager(Config config){
        this.config = config;
    }

    /**
     * Load all bot modules
     */
    public void loadModules(){
        if(this.config.isModuleEnabled("deadlines")){
            TPBot.getLogger().info("[MODULE MANAGER] Loading deadlines module...");
            this.loadDeadlines();
            TPBot.getLogger().info("[MODULE MANAGER] Deadlines module loaded!");
        }
    }

    /**
     * Unload all bot modules
     */
    public void unloadModules(){
        if(this.config.isModuleEnabled("deadlines")){
            TPBot.getLogger().info("[MODULE MANAGER] Unloading deadlines module...");
            this.unloadDeadlines();
            TPBot.getLogger().info("[MODULE MANAGER] Deadlines module unloaded!");
        }
    }

    /**
     * Load all deadlines module
     */
    private void loadDeadlines(){
        TPBot.getDeadLines().clear();
        for(DeadLine deadLine : TPBot.getDeadLinesTable().getDeadLines()){
            if(deadLine.getDeadlineStatus() != DeadlineStatus.PASSED){
                TPBot.getDeadLines().put(deadLine.getId(), deadLine);
                TPBot.getLogger().info(String.format("[DEADLINES] Loaded deadline: %s with ID: %s", deadLine.getName(), deadLine.getId()));
            }
        }
        this.deadlineStatusRunnable = new DeadlineStatusRunnable();
        this.deadlineStatusRunnable.start();
    }

    /**
     * Unload deadline module
     */
    private void unloadDeadlines(){
        TPBot.getDeadLines().clear();
        deadlineStatusRunnable.shutdown();
    }

}
