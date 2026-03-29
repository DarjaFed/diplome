package tests;

import data.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PaymentTests {

    PaymentPage page = new PaymentPage();

    // =========================
    // 1–4 УСПЕШНАЯ И ОТКЛОНЁННАЯ ОПЛАТА
    // =========================

    @Test
    @DisplayName("Сценарий №1: Успешная покупка тура по карте")
    void test1() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

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
    @DisplayName("Сценарий №3: Попытка покупки тура по отклонённой карте")
    void test3() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

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
    // 5–11 ПУСТЫЕ ПОЛЯ
    // =========================

    @Test
    @DisplayName("Сценарий №5: Покупка тура по карте со всеми пустыми полями")
    void test5() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("", "", "", "", "");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №7: Пустой номер карты")
    void test7() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("", "12", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №8: Пустой месяц")
    void test8() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №9: Пустой год")
    void test9() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №10: Пустое поле \"Владелец\"")
    void test10() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №11: Пустой CVV")
    void test11() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    // =========================
    // 12–24 НЕВАЛИДНЫЕ ДАННЫЕ
    // =========================

    @Test
    @DisplayName("Сценарий №12: Покупка тура с некорректным номером карты")
    void test12() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("1234 1234 1234 1234", "12", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №13: Покупка тура с месяцем 13")
    void test13() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "13", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №14: Покупка тура с месяцем 00")
    void test14() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "00", "26", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №15: Покупка тура с прошедшим сроком действия карты")
    void test15() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "20", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №16: Покупка тура с годом более чем на 6 лет вперёд")
    void test16() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "35", "IVAN IVANOV", "123");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №17: Покупка тура с CVV из 2 цифр")
    void test17() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "12");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }

    @Test
    @DisplayName("Сценарий №18: Покупка тура с CVV из 4 цифр")
    void test18() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard("4444 4444 4444 4441", "12", "26", "IVAN IVANOV", "1234");
        page.submit();

        $(".input__sub").shouldBe(visible);
    }
}