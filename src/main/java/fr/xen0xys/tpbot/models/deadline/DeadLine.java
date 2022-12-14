package fr.xen0xys.tpbot.models.deadline;

public class DeadLine {

    private final String id;
    private final String name;
    private final String content;
    private final long endTimestamp;
    private final long channelId;
    private final long mentionRoleId;
    private DeadlineStatus deadlineStatus;

    public DeadLine(String id, String name, String content, long endTimestamp, long channelId, long mentionRoleId, DeadlineStatus deadlineStatus) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.endTimestamp = endTimestamp;
        this.channelId = channelId;
        this.mentionRoleId = mentionRoleId;
        this.deadlineStatus = deadlineStatus;
    }

    public DeadLine(DeadLine oldDeadline){
        this.id = oldDeadline.getId();
        this.name = oldDeadline.getName();
        this.content = oldDeadline.getContent();
        this.endTimestamp = oldDeadline.getEndTimestamp();
        this.channelId = oldDeadline.getChannelId();
        this.mentionRoleId = oldDeadline.getMentionRoleId();
        this.deadlineStatus = oldDeadline.getDeadlineStatus();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getContent() {
        return content;
    }
    public long getEndTimestamp() {
        return endTimestamp;
    }
    public long getChannelId() {
        return channelId;
    }
    public long getMentionRoleId() {
        return this.mentionRoleId;
    }
    public DeadlineStatus getDeadlineStatus() {
        return deadlineStatus;
    }

    public void setDeadlineStatus(DeadlineStatus deadlineStatus) {
        this.deadlineStatus = deadlineStatus;
    }
}
