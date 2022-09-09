package fr.xen0xys.tpbot.models.timetable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class EDTEvent {

    private final String description;
    private final long startTimestamp;
    private final long endTimestamp;
    private final String location;
    private final String summary;

    public EDTEvent(String description, long startTimestamp, long endTimestamp, String location, String summary){
        this.description = description;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.location = location;
        this.summary = summary;
    }

    public EDTEvent(HashMap<String, String> eventHash){
        this.description = eventHash.get("DESCRIPTION");
        this.startTimestamp = this.parseDate(eventHash.get("DTSTART"));
        this.endTimestamp = this.parseDate(eventHash.get("DTEND"));
        this.location = eventHash.get("LOCATION");
        this.summary = eventHash.get("SUMMARY");
    }

    private long parseDate(String date){
        try {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            return formatter.parse(date).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDescription() {
        return description;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getSummary() {
        return summary;
    }
}
