package pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class MainPage {

    public PaymentPage openPaymentPage() {

        $$("button")
                .findBy(text("Купить"))
                .shouldBe(visible)
                .click();

        return new PaymentPage();
    }

    public CreditPage openCreditPage() {

        $$("button")
                .findBy(text("Купить в кредит"))
                .shouldBe(visible)
                .click();

        return new CreditPage();
    }
}