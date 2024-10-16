package connectDB;

import java.sql.*;

public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        String severName = "localhost";
        String databaseName = "KhachSan";
        String username = "sa";
        String password = "Vubui096@";
        String url = "jdbc:sqlserver://" + severName + ":1433;databaseName=" + databaseName;
        con = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static Connection getConnection() {
        return con;
    }
}
