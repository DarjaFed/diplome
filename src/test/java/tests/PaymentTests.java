package tests;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTests {

    private PaymentPage paymentPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        paymentPage = mainPage.openPaymentPage();
    }

    // =========================
    // ПОЗИТИВНЫЕ СЦЕНАРИИ
    // =========================

    @Test
    @DisplayName("Успешная покупка тура по карте")
    void shouldBuyWithApprovedCard() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowSuccessNotification();
    }

    @Test
    @DisplayName("Покупка по отклонённой карте")
    void shouldRejectDeclinedCard() {
        paymentPage.fillForm(
                DataHelper.declinedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowErrorNotification();
    }

    // =========================
    // ПУСТЫЕ ПОЛЯ
    // =========================

    @Test
    @DisplayName("Все поля пустые")
    void shouldShowErrorsForEmptyFields() {
        paymentPage.fillForm("", "", "", "", "");
        paymentPage.submit();

        paymentPage.shouldShowCardRequiredError();
        paymentPage.shouldShowMonthRequiredError();
        paymentPage.shouldShowYearRequiredError();
        paymentPage.shouldShowOwnerRequiredError();
        paymentPage.shouldShowCvcRequiredError();
    }

    @Test
    @DisplayName("Пустой номер карты")
    void shouldShowErrorEmptyCard() {
        paymentPage.fillForm(
                "",
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowCardRequiredError();
    }


    @Test
    @DisplayName("Пустой месяц")
    void shouldShowErrorEmptyMonth() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                "",
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowMonthRequiredError();
    }

    @Test
    @DisplayName("Пустой год")
    void shouldShowErrorEmptyYear() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                "",
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowYearRequiredError();
    }

    @Test
    @DisplayName("Пустой владелец")
    void shouldShowErrorEmptyOwner() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                "",
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowOwnerRequiredError();
    }

    @Test
    @DisplayName("Пустой CVV")
    void shouldShowErrorEmptyCVV() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                ""
        );

        paymentPage.submit();
        paymentPage.shouldShowCvcRequiredError();
    }

    // =========================
    // НЕВАЛИДНЫЕ ДАННЫЕ
    // =========================

    @Test
    @DisplayName("Некорректный номер карты")
    void shouldShowInvalidCardError() {
        paymentPage.fillForm(
                DataHelper.invalidCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Месяц = 13")
    void shouldShowInvalidMonth13() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.invalidMonth13(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Месяц = 00")
    void shouldShowInvalidMonth00() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.invalidMonth00(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Просроченная карта")
    void shouldShowExpiredCard() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.expiredYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowExpiredCardError();
    }

    @Test
    @DisplayName("Год более чем на 6 лет вперёд")
    void shouldShowFutureYearError() {
        paymentPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.futureYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );

        paymentPage.submit();
        paymentPage.shouldShowFutureYearError();
    }
}