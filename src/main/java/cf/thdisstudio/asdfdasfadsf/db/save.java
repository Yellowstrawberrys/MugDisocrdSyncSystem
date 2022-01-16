package cf.thdisstudio.asdfdasfadsf.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static cf.thdisstudio.asdfdasfadsf.Asdfdasfadsf.conn;

public class save {
    public void add(UUID uuid, String disid){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("INSERT INTO `plugin_manage`.`db` (`uuid`, `disid`) VALUES ('"+uuid.toString()+"', '"+disid+"');");
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
