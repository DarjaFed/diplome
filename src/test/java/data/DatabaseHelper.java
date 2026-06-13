package data;

import java.sql.*;

public class DatabaseHelper {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getProperty("db.url"),
                System.getProperty("db.user"),
                System.getProperty("db.password")
        );
    }

    public static void cleanDatabase() throws SQLException {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("DELETE FROM credit_request_entity");
            stmt.executeUpdate("DELETE FROM payment_entity");
            stmt.executeUpdate("DELETE FROM order_entity");
        }
    }

    public static String getLastCreditStatus() throws SQLException {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1"
                )
        ) {
            return rs.next() ? rs.getString("status") : null;
        }
    }

    public static String getLastPaymentStatus() throws SQLException {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"
                )
        ) {
            return rs.next() ? rs.getString("status") : null;
        }
    }
}