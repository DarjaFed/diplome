package tests;

import data.DataHelper;
import data.DatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CreditPage;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTests {

    private CreditPage creditPage;

    @BeforeEach
    void setUp() throws Exception {
        open("http://localhost:8080");

        MainPage mainPage = new MainPage();
        creditPage = mainPage.openCreditPage();

        DatabaseHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Статус APPROVED сохраняется в credit_request_entity")
    void shouldSaveApprovedCredit() throws Exception {

        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        creditPage.submit();
        creditPage.shouldShowSuccessNotification();

        String actual = DatabaseHelper.getLastCreditStatus();

        assertEquals(
                "APPROVED",
                actual
        );
    }

    @Test
    @DisplayName("Статус DECLINED сохраняется в credit_request_entity")
    void shouldSaveDeclinedCredit() throws Exception {

        creditPage.fillForm(
                DataHelper.declinedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        creditPage.submit();
        creditPage.shouldShowErrorNotification();

        String actual = DatabaseHelper.getLastCreditStatus();

        assertEquals(
                "DECLINED",
                actual
        );
    }
}