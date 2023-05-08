package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement transferSumField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        transferSumField.shouldBe(visible);
        fromField.shouldBe(visible);
        transferButton.shouldBe(visible);
    }

    private void fillDataAndSend(DataHelper.Card cardFrom, int sum) {
        transferSumField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        transferSumField.setValue(String.valueOf(sum));
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(cardFrom.getCardNumber());
        transferButton.click();
    }

    private void checkErrorMessage(String errorMessage) {
        $("[data-test-id='error-notification']").should(appear).shouldHave(text(errorMessage));
    }

    public DashboardPage makeSuccessTransfer(DataHelper.Card cardFrom, int sum) {
        fillDataAndSend(cardFrom, sum);
        return new DashboardPage();
    }

    public TransferPage makeNotSuccessTransfer(DataHelper.Card cardFrom, int sum, String errorMessage) {
        fillDataAndSend(cardFrom, sum);
        checkErrorMessage(errorMessage);
        return this;
    }
}
