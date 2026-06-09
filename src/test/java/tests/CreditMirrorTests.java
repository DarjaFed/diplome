package tests;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CreditPage;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditMirrorTests {

    private CreditPage creditPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        creditPage = mainPage.openCreditPage();
    }

    @Test
    @DisplayName("Сценарий 1: Успешное приобретение тура в кредит")
    void shouldBuyWithApprovedCredit() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowSuccessNotification();
    }

    @Test
    @DisplayName("Сценарий 2: Попытка покупки тура в кредит по отклонённой карте")
    void shouldRejectDeclinedCredit() {
        creditPage.fillForm(
                DataHelper.declinedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowErrorNotification();
    }

    @Test
    @DisplayName("Сценарий 3: Все поля пустые")
    void shouldShowErrorsForEmptyFields() {
        creditPage.fillForm("", "", "", "", "");
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 4: Пустой номер карты")
    void shouldShowErrorEmptyCard() {
        creditPage.fillForm(
                "",
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 5: Пустой месяц")
    void shouldShowErrorEmptyMonth() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                "",
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 6: Пустой год")
    void shouldShowErrorEmptyYear() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                "",
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 7: Пустое поле владелец")
    void shouldShowErrorEmptyOwner() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                "",
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 8: Пустой CVV")
    void shouldShowErrorEmptyCVV() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                ""
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Сценарий 9: Некорректный номер карты")
    void shouldShowInvalidCardError() {
        creditPage.fillForm(
                DataHelper.invalidCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 10: Месяц = 13")
    void shouldShowErrorInvalidMonth13() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.invalidMonth13(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 11: Месяц = 00")
    void shouldShowErrorInvalidMonth00() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.invalidMonth00(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Сценарий 12: Прошедший срок действия карты")
    void shouldShowErrorExpiredCard() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.expiredYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowExpiredCardError();
    }

    @Test
    @DisplayName("Сценарий 13: Год более чем на 6 лет вперёд")
    void shouldShowErrorFutureYear() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.futureYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowFutureYearError();
    }

    @Test
    @DisplayName("Сценарий 14: CVV из 2 цифр")
    void shouldShowErrorShortCVV() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.shortCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 15: CVV из 4 цифр")
    void shouldShowErrorLongCVV() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.longCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 16: CVV = 000")
    void shouldShowErrorZeroCVV() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.zeroCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 17: Имя владельца на русском")
    void shouldShowErrorOwnerRussian() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.russianOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 18: Имя владельца с цифрами")
    void shouldShowErrorOwnerWithNumbers() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.ownerWithNumbers(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 19: Имя владельца со спецсимволами")
    void shouldShowErrorOwnerSpecialChars() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.ownerSpecialChars(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 20: Очень короткое имя владельца")
    void shouldShowErrorOwnerShort() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.shortOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }

    @Test
    @DisplayName("Сценарий 21: Очень длинное имя владельца")
    void shouldShowErrorOwnerLong() {
        creditPage.fillForm(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.longOwner(),
                DataHelper.validCVV()
        );
        creditPage.submit();
        creditPage.shouldShowInvalidFormatError();
    }
}