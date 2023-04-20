package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    public int sumCheck(DataHelper.Card cardToCheck) {
        reload();
        String[] splitMessage = ($("[data-test-id='" + cardToCheck.getId() + "']").text()).split(":");
        int sum = Integer.parseInt((splitMessage[1]).substring(0, (splitMessage[1]).indexOf("р.")).trim());
        return sum;
    }

    public TransferPage moneyTransfer(DataHelper.Card cardTo) {
        $("[data-test-id='" + cardTo.getId() + "'] button").click();
        return new TransferPage();
    }

    public DashboardPage reload() {
        $("button[data-test-id='action-reload']").click();
        return this;
    }
}