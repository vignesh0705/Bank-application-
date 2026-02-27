package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bankproject?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vicky@07";

    private static Connection con = null;

    // Private constructor (Prevents object creation)
    private DBconnection() { }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database Connected Successfully");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed");
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("🔒 Connection Closed Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DBconnection.getConnection();
        DBconnection.closeConnection();
    }
}