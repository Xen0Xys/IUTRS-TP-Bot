package fr.xen0xys.tpbot.models;

import fr.xen0xys.tpbot.TPBot;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatus;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

public abstract class Utils {

    @SuppressWarnings("unused")
    public static void updateEdtFile(){
        try (BufferedInputStream in = new BufferedInputStream(new URL(TPBot.getConfiguration().getTimetableURL()).openStream());
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

    /**
     * Generate custom id with format "#XXXXX" where X is a random number or capital letter
     * @return String that is custom id
     */
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

    /**
     * Get the current deadline status from deadline end timestamp
     * @param deadlineTimestamp Deadline end timestamp
     * @return DeadlineStatus object
     */
    public static DeadlineStatus getDeadlineStatusFromTimestamp(long deadlineTimestamp){
        for(DeadlineStatus deadlineStatus : DeadlineStatus.values()){
            if(deadlineTimestamp - (System.currentTimeMillis() / 1000) > deadlineStatus.getDuration()){
                return deadlineStatus;
            }
        }
        return DeadlineStatus.END;
    }

    /**
     * Get the discord formatting for timestamp from given timestamp
     * @param timestamp Long that represent a timestamp
     * @return String that represent the discord formatting for timestamp
     */
    public static String getDueDateFromTimestamp(long timestamp){
        return String.format("<t:%d:f>", timestamp);
    }

    /**
     * Get the current timezone
     * @return String that represent the current timezone
     */
    public static String getCurrentTimezone(){
        Calendar now = Calendar.getInstance();
        TimeZone timeZone = now.getTimeZone();
        return timeZone.getID();
    }
}
