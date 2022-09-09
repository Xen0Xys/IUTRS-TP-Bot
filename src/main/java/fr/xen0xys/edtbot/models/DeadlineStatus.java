package fr.xen0xys.edtbot.models;

public enum DeadlineStatus {

    MONTH(30*24*60*60L),
    WEEK(7*24*60*60L),
    THREE_DAYS(3*24*60*60L),
    DAY(24*60*60L),
    HOUR(60*60L),
    TEN_MINUTES(10*60L),
    ENDED(0L);

    private final long msDuration;

    DeadlineStatus(long msDuration){
        this.msDuration = msDuration;
    }

    public long getDuration() {
        return msDuration;
    }
}
