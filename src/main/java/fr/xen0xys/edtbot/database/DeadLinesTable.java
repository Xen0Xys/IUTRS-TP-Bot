package fr.xen0xys.edtbot.database;

import fr.xen0xys.edtbot.models.Utils;
import fr.xen0xys.xen0lib.database.Database;
import fr.xen0xys.xen0lib.database.Table;
import fr.xen0xys.xen0lib.utils.Status;

public class DeadLinesTable extends Table {

    public DeadLinesTable(String tableName, Database database) {
        super(tableName, database);
    }

    public Status addDeadLine(String name, String content, long endTimestamp, long channelId){
        String query = String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', %d, %d)", this.getTableName(), Utils.generateId(), name, content, endTimestamp, channelId);
        return this.getDatabase().executeUpdateQuery(query);
    }

    public boolean isIdExist(String uid){
        String query = String.format("SELECT * FROM %s WHERE uid = '%s'", this.getTableName(), uid);
        return this.getDatabase().isDataExist(this.getDatabase().executeQuery(query)) == Status.Exist;
    }

    public Status deleteDeadLine(String uid){
        String query = String.format("DELETE FROM %s WHERE uid = '%s'", this.getTableName(), uid);
        return this.getDatabase().executeUpdateQuery(query);
    }

}
