package cf.thdisstudio.asdfdasfadsf.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static cf.thdisstudio.asdfdasfadsf.Asdfdasfadsf.conn;

public class load {
    public boolean isGood(UUID uuid){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("use plugin_manage");
            ResultSet rt = stmt.executeQuery("SELECT * FROM db WHERE uuid LIKE '%" + uuid.toString() + "%';");
            conn.commit();
            return rt.first();
        } catch (Exception throwables) {
            return false;
        }
    }
}
