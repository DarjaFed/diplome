package tests;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PaymentTest {

    PaymentPage page = new PaymentPage();

    @Test
    void shouldBuyWithApprovedCard() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".notification_status_ok")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldDeclineCard() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.declinedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".notification_status_error")
                .shouldBe(visible, Duration.ofSeconds(10));
    }
    @Test
    void shouldShowErrorIfCardNumberEmpty() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                "",
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfMonthEmpty() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                "",
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfYearEmpty() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                "",
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfOwnerEmpty() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                "",
                DataHelper.validCVV()
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfCVVEmpty() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                ""
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfCardNumberInvalid() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                "1111 1111 1111 1111",
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".notification_status_error")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfMonthIs00() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                "00",
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()

        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfYearExpired() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                "12",
                "20",
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();

        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfOwnerInRussian() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                "ИВАНОВ ИВАН",
                DataHelper.validCVV()
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldShowErrorIfCVVLessThan3Digits() {
        open("http://localhost:8080");
        $$("button").findBy(text("Купить")).click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                "12"
        );
        page.submit();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(10));
    }
}