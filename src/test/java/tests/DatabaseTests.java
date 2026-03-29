package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTests {

    private String url = System.getProperty("db.url",
            "jdbc:mysql://localhost:3306/app");

    private String user = System.getProperty("db.user", "user");
    private String password = System.getProperty("db.password", "pass");

    @Test
    @DisplayName("Проверка записи APPROVED в базе данных")
    void shouldSaveApprovedPayment() throws Exception {

        Connection conn = DriverManager.getConnection(url, user, password);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"
        );

        rs.next();
        assertEquals("APPROVED", rs.getString("status"));
    }

    @Test
    @DisplayName("Проверка записи DECLINED в базе данных")
    void shouldSaveDeclinedPayment() throws Exception {

        Connection conn = DriverManager.getConnection(url, user, password);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"
        );

        rs.next();
        assertEquals("DECLINED", rs.getString("status"));
    }
}