package tests;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PaymentTests {

    PaymentPage page = new PaymentPage();

    @BeforeEach
    void setUp() {
        // Настройка Selenide и открытие страницы
        open("http://localhost:8080");
    }

    // =========================
    // 1–4: Позитивная и отклонённая оплата
    // =========================

    @Test
    @DisplayName("Сценарий №1: Успешная покупка тура по карте")
    void test1() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Сценарий №2: Успешное приобретение тура в кредит")
    void test2() {
        $$("button").findBy(text("Купить в кредит")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Сценарий №3: Попытка покупки тура по отклонённой карте")
    void test3() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.declinedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Сценарий №4: Попытка покупки тура в кредит по отклонённой карте")
    void test4() {
        $$("button").findBy(text("Купить в кредит")).click();
        page.payWithCard(DataHelper.declinedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(10));
    }

    // =========================
    // 5–11: Пустые поля
    // =========================

    @Test
    @DisplayName("Сценарий №5: Все поля пустые")
    void test5() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard("", "", "", "", "");
        page.submit();
        $$("span.input__sub").filter(visible).forEach(el ->
                el.shouldHave(text("Поле обязательно для заполнения")));
    }

    @Test
    @DisplayName("Сценарий №6: Все поля пустые в кредите")
    void test6() {
        $$("button").findBy(text("Купить в кредит")).click();
        page.payWithCard("", "", "", "", "");
        page.submit();
        $$("span.input__sub").filter(visible).forEach(el ->
                el.shouldHave(text("Поле обязательно для заполнения")));
    }

    @Test
    @DisplayName("Сценарий №7: Пустой номер карты")
    void test7() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard("", DataHelper.validMonth(), DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий №8: Пустой месяц")
    void test8() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), "", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий №9: Пустой год")
    void test9() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(), "",
                DataHelper.validOwner(), DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий №10: Пустое поле Владелец")
    void test10() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "", DataHelper.validCVV());
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Сценарий №11: Пустой CVV")
    void test11() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "");
        page.submit();
        $(".input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    // =========================
    // 12–24: Невалидные данные
    // =========================

    @Test
    @DisplayName("Сценарий №12: Некорректный номер карты")
    void test12() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard("1234 1234 1234 1234", DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №13: Месяц = 13")
    void test13() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), "13", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №14: Месяц = 00")
    void test14() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), "00", DataHelper.validYear(),
                DataHelper.validOwner(), DataHelper.validCVV());
        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №15: Прошедший срок карты")
    void test15() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(), "20",
                DataHelper.validOwner(), DataHelper.validCVV());
        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №16: Год более чем на 6 лет вперёд")
    void test16() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(), "35",
                DataHelper.validOwner(), DataHelper.validCVV());
        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №17: CVV из 2 цифр")
    void test17() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "12");
        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №18: CVV из 4 цифр")
    void test18() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "1234");
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №19: CVV = 000")
    void test19() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), DataHelper.validOwner(), "000");
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №20: Имя владельца на русском")
    void test20() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "ИВАН ИВАНОВ", DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №21: Имя владельца с цифрами")
    void test21() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IVAN123", DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №22: Имя владельца со спецсимволами")
    void test22() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IV@N IVANOV", DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №23: Очень короткое имя владельца")
    void test23() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "I", DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №24: Очень длинное имя владельца")
    void test24() {
        $$("button").findBy(text("Купить")).click();
        page.payWithCard(DataHelper.approvedCard(), DataHelper.validMonth(),
                DataHelper.validYear(), "IVAN IVANOV IVANOV IVANOV", DataHelper.validCVV());
        $$(".input__sub").findBy(text("Неверный формат")).shouldBe(visible);
    }
}