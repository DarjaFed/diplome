package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.text;

public class PaymentPage {

    private SelenideElement cardNumber = $$("input.input__control").get(0);
    private SelenideElement month = $$("input.input__control").get(1);
    private SelenideElement year = $$("input.input__control").get(2);
    private SelenideElement owner = $$("input.input__control").get(3);
    private SelenideElement cvc = $$("input.input__control").get(4);

    private SelenideElement continueButton = $$("button").findBy(text("Продолжить"));

    public void payWithCard(String number, String m, String y, String o, String code) {
        cardNumber.setValue(number);
        month.setValue(m);
        year.setValue(y);
        owner.setValue(o);
        cvc.setValue(code);
    }

    public void submit() {
        continueButton.click();
    }
}