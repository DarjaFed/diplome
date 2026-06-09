package data;

import java.sql.*;

public class DatabaseHelper {

    private static final String db =
            System.getProperty("db", "mysql");

    private static String getUrl() {
        if ("postgres".equals(db)) {
            return "jdbc:postgresql://localhost:5432/app";
        }
        return "jdbc:mysql://localhost:3306/app?allowPublicKeyRetrieval=true&useSSL=false";
    }

    private static String getUser() {
        return "user";
    }

    private static String getPassword() {
        return "pass";
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                getUrl(),
                getUser(),
                getPassword()
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
            return rs.next()
                    ? rs.getString("status")
                    : null;
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
            return rs.next()
                    ? rs.getString("status")
                    : null;
        }
    }
}