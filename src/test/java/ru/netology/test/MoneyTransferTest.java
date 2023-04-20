package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
        // Логинимся и возвращаем SUT в исходное состояние после предыдущих тестов
    void shouldLoginAndRestoreSUT() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getValidAuthInfo();
        var validLogin = new LoginPage()
                .validLogin(authInfo)
                .validVerify(DataHelper.getVerificationCodeFor(authInfo));

        int card1Sum = validLogin.sumCheck(DataHelper.getCard1(authInfo));

        if (card1Sum < 10_000) {
            validLogin.moneyTransfer(DataHelper.getCard1(authInfo))
                    .makeSuccessTransfer(DataHelper.getCard2(authInfo), 10_000 - card1Sum);
        } else {
            if (card1Sum > 10_000) {
                validLogin.moneyTransfer(DataHelper.getCard2(authInfo))
                        .makeSuccessTransfer(DataHelper.getCard1(authInfo), card1Sum - 10_000);
            }
        }
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var authInfo = DataHelper.getValidAuthInfo();
        var dashboard = new DashboardPage();

        int card1BeforeTransfer = dashboard.sumCheck(DataHelper.getCard1(authInfo));
        int card2BeforeTransfer = dashboard.sumCheck(DataHelper.getCard2(authInfo));

        int sumForTransfer = 9000;
        dashboard.moneyTransfer(DataHelper.getCard2(authInfo))
                .makeSuccessTransfer(DataHelper.getCard1(authInfo), sumForTransfer);

        int card1AfterTransfer = dashboard.sumCheck(DataHelper.getCard1(authInfo));
        int card2AfterTransfer = dashboard.sumCheck(DataHelper.getCard2(authInfo));

        Assertions.assertEquals((card1BeforeTransfer - sumForTransfer), card1AfterTransfer);
        Assertions.assertEquals((card2BeforeTransfer + sumForTransfer), card2AfterTransfer);
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var authInfo = DataHelper.getValidAuthInfo();
        var dashboard = new DashboardPage();

        int card1BeforeTransfer = dashboard.sumCheck(DataHelper.getCard1(authInfo));
        int card2BeforeTransfer = dashboard.sumCheck(DataHelper.getCard2(authInfo));

        int sumForTransfer = 4000;
        dashboard.moneyTransfer(DataHelper.getCard1(authInfo))
                .makeSuccessTransfer(DataHelper.getCard2(authInfo), sumForTransfer);

        int card1AfterTransfer = dashboard.sumCheck(DataHelper.getCard1(authInfo));
        int card2AfterTransfer = dashboard.sumCheck(DataHelper.getCard2(authInfo));

        Assertions.assertEquals((card2BeforeTransfer - sumForTransfer), card2AfterTransfer);
        Assertions.assertEquals((card1BeforeTransfer + sumForTransfer), card1AfterTransfer);
    }

    @Test
    void shouldNotTransferMoneyBecauseAmountIsNotEnough() {
        var authInfo = DataHelper.getValidAuthInfo();
        var dashboard = new DashboardPage();

        int sumForTransfer = 12000;
        dashboard.moneyTransfer(DataHelper.getCard1(authInfo))
                .makeNotSuccessTransfer(DataHelper.getCard2(authInfo), sumForTransfer);
    }

    @Test
    void shouldShowErrorMessageIfBadCardNumber() {
        var authInfo = DataHelper.getValidAuthInfo();

        int sumForTransfer = 3000;
        new DashboardPage().moneyTransfer(DataHelper.getCard2(authInfo))
                .makeNotSuccessTransfer(DataHelper.getInvalidCard(authInfo), sumForTransfer);
    }
}

