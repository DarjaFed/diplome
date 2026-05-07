package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;

public class PaymentPage {

    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$("input");
    private SelenideElement month = $(byText("Месяц")).parent().$("input");
    private SelenideElement year = $(byText("Год")).parent().$("input");
    private SelenideElement owner = $(byText("Владелец")).parent().$("input");
    private SelenideElement cvc = $(byText("CVC/CVV")).parent().$("input");

    private SelenideElement continueButton = $$("button").findBy(text("Продолжить"));

    public void fillForm(String number, String m, String y, String o, String code) {
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