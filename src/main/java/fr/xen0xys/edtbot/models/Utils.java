package fr.xen0xys.edtbot.models;

import fr.xen0xys.edtbot.EDTBot;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public abstract class Utils {
    public static void updateEdtFile(){
        try (BufferedInputStream in = new BufferedInputStream(new URL(EDTBot.getConfiguration().getEdtUrl()).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(EDTBot.getDataFolder().getPath() + "/edt.ics")) {
            byte dataBuffer[] = new byte[1024];
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
}
