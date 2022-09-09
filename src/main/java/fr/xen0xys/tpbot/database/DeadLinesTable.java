package fr.xen0xys.tpbot.database;

import fr.xen0xys.tpbot.models.deadline.DeadLine;
import fr.xen0xys.tpbot.models.deadline.DeadlineStatus;
import fr.xen0xys.xen0lib.database.Database;
import fr.xen0xys.xen0lib.database.Table;
import fr.xen0xys.xen0lib.utils.Status;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeadLinesTable extends Table {

    public DeadLinesTable(String tableName, Database database) {
        super(tableName, database);
    }

    public Status addDeadLine(DeadLine deadLine){
        String query = String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', %d, %d, '%s')", this.getTableName(), deadLine.getId(), deadLine.getName(), deadLine.getContent(), deadLine.getEndTimestamp(), deadLine.getChannelId(), deadLine.getDeadlineStatus());
        return this.getDatabase().executeUpdateQuery(query);
    }

    public Status deleteDeadLine(String uid){
        String query = String.format("DELETE FROM %s WHERE id = '%s'", this.getTableName(), uid);
        return this.getDatabase().executeUpdateQuery(query);
    }

    public List<DeadLine> getDeadLines(){
        List<DeadLine> deadLines = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", this.getTableName());
        try (ResultSet rs = this.getDatabase().executeQuery(query)) {
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String content = rs.getString("content");
                long endTimestamp = rs.getLong("endTimestamp");
                long channelId = rs.getLong("channelId");
                DeadlineStatus deadlineStatus = DeadlineStatus.valueOf(rs.getString("status"));
                deadLines.add(new DeadLine(id, name, content, endTimestamp, channelId, deadlineStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deadLines;
    }

    public Status updateDeadlineStatus(String id, DeadlineStatus deadlineStatus){
        String query = String.format("UPDATE %s SET status = '%s' WHERE id = '%s'", this.getTableName(), deadlineStatus, id);
        return this.getDatabase().executeUpdateQuery(query);
    }

}
