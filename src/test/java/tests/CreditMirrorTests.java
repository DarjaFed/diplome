package tests;

import data.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CreditMirrorTests {

    PaymentPage page = new PaymentPage();

    // =========================
    // 2–4 УСПЕШНАЯ И ОТКЛОНЁННАЯ ОПЛАТА В КРЕДИТ
    // =========================

    @Test
    @DisplayName("Сценарий №2: Успешное приобретение тура в кредит")
    void test2() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard(
                "4444 4444 4444 4441",
                "12",
                "26",
                "IVAN IVANOV",
                "123"
        );

        page.submit();
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Сценарий №4: Попытка покупки тура в кредит по отклонённой карте")
    void test4() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard(
                "4444 4444 4444 4442",
                "12",
                "26",
                "IVAN IVANOV",
                "123"
        );

        page.submit();
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(10));
    }

    // =========================
    // 6 ВСЕ ПОЛЯ ПУСТЫЕ
    // =========================

    @Test
    @DisplayName("Сценарий №6: Покупка тура в кредит со всеми пустыми полями")
    void test6() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("", "", "", "", "");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    // =========================
    // 7–11 ПУСТЫЕ ПОЛЯ ПО ОТДЕЛЬНОСТИ
    // =========================

    @Test
    @DisplayName("Сценарий №7: Пустой номер карты (кредит)")
    void test7() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("", "12", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №8: Пустой месяц (кредит)")
    void test8() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №9: Пустой год (кредит)")
    void test9() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №10: Пустое поле \"Владелец\" (кредит)")
    void test10() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №11: Пустой CVV (кредит)")
    void test11() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    // =========================
    // 12–18 НЕВАЛИДНЫЕ ДАННЫЕ
    // =========================

    @Test
    @DisplayName("Сценарий №12: Покупка тура в кредит с некорректным номером карты")
    void test12() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("1234 1234 1234 1234", "12", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №13: Покупка тура в кредит с месяцем 13")
    void test13() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "13", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №14: Покупка тура в кредит с месяцем 00")
    void test14() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "00", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №15: Покупка тура в кредит с прошедшим сроком действия карты")
    void test15() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "20", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №16: Покупка тура в кредит с годом более чем на 6 лет вперёд")
    void test16() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "35", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №17: Покупка тура в кредит с CVV из 2 цифр")
    void test17() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "12");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №18: Покупка тура в кредит с CVV из 4 цифр")
    void test18() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить в кредит")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "1234");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }
}