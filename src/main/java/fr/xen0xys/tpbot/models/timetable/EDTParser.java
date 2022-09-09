package fr.xen0xys.tpbot.models.timetable;

import fr.xen0xys.tpbot.TPBot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class EDTParser {

    private final BufferedReader bufferedInputStream;

    public EDTParser(){
        try {
            InputStream inputStream = new FileInputStream(TPBot.getDataFolder() + "/edt.ics");
            this.bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EDTEvent> parseEdt(){
        List<EDTEvent> events = new ArrayList<>();
        try {
            String line;
            while((line = this.bufferedInputStream.readLine()) != null){
                if(line.equals("BEGIN:VEVENT")){
                    HashMap<String, String> eventHash = new HashMap<>();
                    while(!(line = this.bufferedInputStream.readLine()).equals("END:VEVENT")){
                        eventHash.put(line.split(":")[0], String.join("", Arrays.copyOfRange(line.split(":"), 1, line.split(":").length)));
                    }
                    events.add(new EDTEvent(eventHash));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

}
