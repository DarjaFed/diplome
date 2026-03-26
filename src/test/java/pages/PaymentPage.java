package pages;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;

public class PaymentPage {

    public void payWithCard(String cardNumber, String month, String year, String owner, String cvv) {

        $$("input").get(0).setValue(cardNumber);
        $$("input").get(1).setValue(month);
        $$("input").get(2).setValue(year);
        $$("input").get(3).setValue(owner);
        $$("input").get(4).setValue(cvv);
    }

    public void submit() {
        $$("button").findBy(text("Продолжить"))
                .shouldBe(visible)
                .click();
    }
}