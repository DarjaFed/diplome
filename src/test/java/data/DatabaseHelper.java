package data;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";

    public static void cleanDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM credit_request_entity");
            stmt.executeUpdate("DELETE FROM payment_entity");
            stmt.executeUpdate("DELETE FROM order_entity");
        }
    }

    public static String getLastCreditStatus() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1"
             )) {

            if (rs.next()) {
                return rs.getString("status");
            }
            return null;
        }
    }

    public static String getLastPaymentStatus() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"
             )) {

            if (rs.next()) {
                return rs.getString("status");
            }
            return null;
        }
    }
}