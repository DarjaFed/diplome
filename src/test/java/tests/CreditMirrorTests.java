package tests;
import data.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;

public class CreditMirrorTests {

    PaymentPage page = new PaymentPage();

    @BeforeEach
    void setUp() {
        // Настройки Selenide

        open("http://localhost:8080");
    }

    // =========================
    // 1–2: Позитивные сценарии
    // =========================
    @Test
    @DisplayName("Сценарий 2: Успешное приобретение тура в кредит")
    void shouldBuyWithApprovedCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(10));
    }

    // =========================
    // 4: Негативный сценарий с отклонённой картой
    // =========================
    @Test
    @DisplayName("Сценарий 4: Попытка покупки тура в кредит по отклонённой карте")
    void shouldRejectDeclinedCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.declinedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(10));
    }

    // =========================
    // 6–24: Проверка валидации формы
    // =========================

    // Сценарии 6: все поля пустые
    @Test
    @DisplayName("Сценарий 6: Покупка в кредит со всеми пустыми полями")
    void shouldShowErrorsEmptyFieldsCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard("", "", "", "", "");
        page.submit();
        $$("span.input__sub").filter(visible).forEach(el ->
                el.shouldHave(text("Поле обязательно для заполнения")));
    }

    @Test
    @DisplayName("Сценарий 7: Пустой номер карты")
    void shouldShowErrorEmptyCardCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard("", DataHelper.validMonth(), DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий 8: Пустой месяц")
    void shouldShowErrorEmptyMonthCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), "", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий 9: Пустой год")
    void shouldShowErrorEmptyYearCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(), "",
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий 10: Пустое поле 'Владелец'")
    void shouldShowErrorEmptyOwnerCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий 11: Пустой CVV")
    void shouldShowErrorEmptyCVVCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "");
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий 12: Некорректный номер карты")
    void shouldShowErrorInvalidCardNumberCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard("1234 1234 1234 1234", DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 13: Месяц = 13")
    void shouldShowErrorInvalidMonth13Credit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), "13", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 14: Месяц = 00")
    void shouldShowErrorInvalidMonth00Credit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), "00", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 15: Прошедший срок действия карты")
    void shouldShowErrorExpiredCardCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), "12", "20",
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Истёк срок действия карты"));
    }

    @Test
    @DisplayName("Сценарий 16: Год более чем на 6 лет вперёд")
    void shouldShowErrorFutureYearCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(), "33",
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный срок действия карты"));
    }

    @Test
    @DisplayName("Сценарий 17: CVV из 2 цифр")
    void shouldShowErrorShortCVVCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "12");
        page.submit();
        $$(".input__sub").findBy(text("Неверный формат"))
                .shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Сценарий 18: CVV из 4 цифр")
    void shouldShowErrorLongCVVCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "1234");
        page.submit();
        $$(".input__sub").findBy(text("Неверный формат"))
                .shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Сценарий 19: CVV = 000")
    void shouldShowErrorCVVCredit000() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "000");
        page.submit();
        $$(".input__sub").findBy(text("Неверный формат"))
                .shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Сценарий 20: Имя владельца на русском")
    void shouldShowErrorOwnerRussianCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "ИВАН ИВАНОВ", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 21: Имя владельца с цифрами")
    void shouldShowErrorOwnerWithNumbersCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IVAN123", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 22: Имя владельца со спецсимволами")
    void shouldShowErrorOwnerSpecialCharsCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IV@N IVANOV", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 23: Очень короткое имя владельца")
    void shouldShowErrorOwnerShortCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "I", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }

    @Test
    @DisplayName("Сценарий 24: Очень длинное имя владельца")
    void shouldShowErrorOwnerLongCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IVAN IVANOV IVANOV IVANOV", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Неверный формат"));
    }
}