package tests;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import pages.PaymentPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CreditTest {

    PaymentPage page = new PaymentPage();

    @Test
    void shouldBuyInCreditApproved() {
        open("http://localhost:8080");
        $("button").click();

        page.payWithCard(
                DataHelper.approvedCard(),
                DataHelper.validMonth(),
                DataHelper.validYear(),
                DataHelper.validOwner(),
                DataHelper.validCVV()
        );
        page.submit();
        $(".notification_visible")
                .shouldBe(visible)
                .shouldHave(text("Операция одобрена"));
    }
}