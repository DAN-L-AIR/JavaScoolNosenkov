package hw16;
//import org.apache.commons.io.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSourceHelper {
    private static final String queryCreate = "CREATE TABLE IF NOT EXISTS CacheTable(arg INT, result ARRAY);";
    private static String dbPath = "";
    public static void SetDBPath(String _dbPath){
        dbPath = _dbPath;
    }
    public static Connection connection() throws SQLException {
        String url = "jdbc:h2:" + dbPath + "/fibDB";
        final Connection connection = DriverManager.getConnection(url, "sa", "");
        connection.setAutoCommit(true);
        return connection;
    }

    public static void CreateDb() {
        try (PreparedStatement preparedStatement = DataSourceHelper.connection().prepareStatement(queryCreate)){
            preparedStatement.execute();
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }