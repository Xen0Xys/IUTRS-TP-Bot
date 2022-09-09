package fr.xen0xys.tpbot.models;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatus;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public abstract class Utils {
    @SuppressWarnings("unused")
    public static void updateEdtFile(){
        try (BufferedInputStream in = new BufferedInputStream(new URL(TPBot.getConfiguration().getEdtUrl()).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(TPBot.getDataFolder().getPath() + "/edt.ics")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // A method that return a random unique id with one "#" and 5 capitals letters or numbers for a new deadline
    public static String generateId(){
        StringBuilder id = new StringBuilder("#");
        for(int i = 0; i < 5; i++){
            int random = (int) (Math.random() * 36);
            if(random < 10){
                id.append(random);
            }else{
                id.append((char) (random + 55));
            }
        }
        return id.toString();
    }

    public static DeadlineStatus getDeadlineStatusFromTimestamp(long deadlineTimestamp){
        for(DeadlineStatus deadlineStatus : DeadlineStatus.values()){
            if(deadlineTimestamp - (System.currentTimeMillis() / 1000) > deadlineStatus.getDuration()){
                return deadlineStatus;
            }
        }
        return DeadlineStatus.ENDED;
    }

    public static String getDueDateFromTimestamp(long timestamp){
        return String.format("<t:%d:f>", timestamp);
    }
}
