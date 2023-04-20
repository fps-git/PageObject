package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement transferSumField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");

    public TransferPage() {
        transferSumField.shouldBe(visible);
        fromField.shouldBe(visible);
    }

    public DashboardPage makeSuccessTransfer(DataHelper.Card cardFrom, int sum) {
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom.getCardNumber());
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public TransferPage makeNotSuccessTransfer(DataHelper.Card cardFrom, int sum) {
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(String.valueOf(sum));
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(cardFrom.getCardNumber());
        $("[data-test-id='action-transfer']").click();
        $("[data-test-id='error-notification']").should(appear);
        return this;
    }
}
