package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;

public class PaymentPage {

    private SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("input[placeholder='08']");
    private SelenideElement year = $("input[placeholder='22']");
    private SelenideElement owner = $("input[placeholder='IVAN IVANOV']");
    private SelenideElement cvv = $("input[placeholder='999']");
    private SelenideElement continueButton = $("button.button");

    public void payWithCard(String card, String m, String y, String o, String c) {
        cardNumber.setValue(card);
        month.setValue(m);
        year.setValue(y);
        owner.setValue(o);
        cvv.setValue(c);
    }

    public void submit() {
        continueButton.shouldBe(visible).click();
    }
}