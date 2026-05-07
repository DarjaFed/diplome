package tests;

import data.DataHelper;
import data.DatabaseHelper;
import org.junit.jupiter.api.*;

import pages.PaymentPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTests {

    PaymentPage page = new PaymentPage();

    @BeforeEach
    void setUp() throws Exception {
        open("http://localhost:8080");
        DatabaseHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Должен сохраняться статус APPROVED в credit_request_entity")
    void shouldSaveApprovedCredit() throws Exception {
        $$("button").findBy(text("Купить в кредит")).click();

        page.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();

        $(".notification_status_ok").shouldBe(visible);

        String status = DatabaseHelper.getLastCreditStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    @DisplayName("Должен сохраняться статус DECLINED в credit_request_entity")
    void shouldSaveDeclinedCredit() throws Exception {
        $$("button").findBy(text("Купить в кредит")).click();

        page.fillForm(
                DataHelper.declinedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();

        $(".notification_status_error").shouldBe(visible);

        String status = DatabaseHelper.getLastCreditStatus();
        assertEquals("DECLINED", status);
    }
}