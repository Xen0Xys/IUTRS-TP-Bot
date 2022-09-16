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

    /**
     *
     * @param deadLine Deadline object
     * @return Custom Status from Xen0Lib: Success, SQLError
     */
    public Status addDeadLine(DeadLine deadLine){
        String injectionProofContent = deadLine.getContent();
        injectionProofContent = injectionProofContent.replace("'", "%simple_quote%");
        String injectionProofName = deadLine.getName();
        injectionProofName = injectionProofName.replace("'", "%simple_quote%");
        String query = String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', %d, %d, '%s')", this.getTableName(), deadLine.getId(), injectionProofName, injectionProofContent, deadLine.getEndTimestamp(), deadLine.getChannelId(), deadLine.getDeadlineStatus());
        return this.getDatabase().executeUpdateQuery(query);
    }

    /**
     * Delete deadline from database using its id
     * @param id Custom deadline id generated from TPBot Utils
     * @return Custom Status from Xen0Lib: Success, SQLError
     */
    public Status deleteDeadLine(String id){
        String query = String.format("DELETE FROM %s WHERE id = '%s'", this.getTableName(), id);
        return this.getDatabase().executeUpdateQuery(query);
    }

    /**
     * Get all actives deadlines from database (ignore deadlines with status PASSED)
     * @return List of DeadLine objects from the database
     */
    public List<DeadLine> getDeadLines(){
        List<DeadLine> deadLines = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE status!='%s'", this.getTableName(), DeadlineStatus.PASSED);
        try (ResultSet rs = this.getDatabase().executeQuery(query)) {
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String content = rs.getString("content");
                String injectionProofContent = content.replace("%simple_quote%", "'");
                String injectionProofName = name.replace("%simple_quote%", "'");
                long endTimestamp = rs.getLong("endTimestamp");
                long channelId = rs.getLong("channelId");
                DeadlineStatus deadlineStatus = DeadlineStatus.valueOf(rs.getString("status"));
                deadLines.add(new DeadLine(id, injectionProofName, injectionProofContent, endTimestamp, channelId, deadlineStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deadLines;
    }

    /**
     * Update deadline status on database
     * @param id Custom deadline id generated from TPBot Utils
     * @param deadlineStatus DeadlineStatus object from enum
     * @return Custom Status from Xen0Lib: Success, SQLError
     */
    public Status updateDeadlineStatus(String id, DeadlineStatus deadlineStatus){
        String query = String.format("UPDATE %s SET status = '%s' WHERE id = '%s'", this.getTableName(), deadlineStatus, id);
        return this.getDatabase().executeUpdateQuery(query);
    }

}
