package pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    private final SelenideElement cardNumber =
            $("[placeholder='0000 0000 0000 0000']");

    private final SelenideElement month =
            $("[placeholder='08']");

    private final SelenideElement year =
            $("[placeholder='22']");

    private final SelenideElement owner =
            $$("input.input__control").get(3);

    private final SelenideElement cvv =
            $("[placeholder='999']");

    private final SelenideElement continueButton =
            $$("button")
                    .findBy(text("Продолжить"));

    private final SelenideElement successNotification =
            $(".notification_status_ok");

    private final SelenideElement errorNotification =
            $(".notification_status_error");

    public void fillForm(
            String card,
            String monthValue,
            String yearValue,
            String ownerValue,
            String cvvValue
    ) {

        cardNumber.clear();
        cardNumber.setValue(card);

        month.clear();
        month.setValue(monthValue);

        year.clear();
        year.setValue(yearValue);

        owner.clear();
        owner.setValue(ownerValue);

        cvv.clear();
        cvv.setValue(cvvValue);
    }

    public void submit() {
        continueButton.shouldBe(enabled).click();
    }

    public void shouldShowSuccessNotification() {
        successNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком"));
    }

    public void shouldShowErrorNotification() {
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка"));
    }

    public void shouldShowValidationError(String message) {
        $$("span.input__sub")
                .findBy(text(message))
                .shouldBe(visible);
    }

    public void shouldShowInvalidFormatError() {
        shouldShowValidationError("Неверный формат");
    }

    public void shouldShowExpiredCardError() {
        shouldShowValidationError("Истёк срок действия карты");
    }

    public void shouldShowFutureYearError() {
        shouldShowValidationError("Неверно указан срок действия карты");
    }
    public void shouldShowCardRequiredError() {
        shouldShowValidationError("Поле обязательно для заполнения");
    }

    public void shouldShowMonthRequiredError() {
        shouldShowValidationError("Поле обязательно для заполнения");
    }

    public void shouldShowYearRequiredError() {
        shouldShowValidationError("Поле обязательно для заполнения");
    }

    public void shouldShowOwnerRequiredError() {
        shouldShowValidationError("Поле обязательно для заполнения");
    }

    public void shouldShowCvcRequiredError() {
        shouldShowValidationError("Поле обязательно для заполнения");
    }
}
