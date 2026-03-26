package tests;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    @Test
    void shouldSaveApprovedPayment() throws Exception {

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app",
                "user",
                "pass"
        );

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1");

        rs.next();

        assertEquals("APPROVED", rs.getString("status"));
    }
}