package fr.xen0xys.tpbot.models.deadline;

public enum DeadlineStatus {

    MONTH(30*24*60*60L, "T-1 Mois"),
    WEEK(7*24*60*60L, "T-1 Semaine"),
    THREE_DAYS(3*24*60*60L, "T-3 Jours"),
    DAY(24*60*60L, "T-1 Jour"),
    HOUR(60*60L, "T-1 Heure"),
    TEN_MINUTES(10*60L, "T-10 Minutes"),
    END(0L, "T-0"),
    PASSED(Long.MIN_VALUE, "Passé");

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
