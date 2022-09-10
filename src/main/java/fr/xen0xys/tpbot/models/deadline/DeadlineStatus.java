package fr.xen0xys.tpbot.models.deadline;

public enum DeadlineStatus {

    MONTH(30*24*60*60L, "1 Mois"),
    WEEK(7*24*60*60L, "1 Semaine"),
    THREE_DAYS(3*24*60*60L, "3 Jours"),
    DAY(24*60*60L, "1 Jour"),
    HOUR(60*60L, "1 Heure"),
    TEN_MINUTES(10*60L, "10 Minutes"),
    ENDED(0L, "Terminé");

    private final long msDuration;
    private final String display;

    DeadlineStatus(long msDuration, String display){
        this.msDuration = msDuration;
        this.display = display;
    }

    public long getDuration() {
        return msDuration;
    }
    public String getDisplay() {
        return display;
    }
}
