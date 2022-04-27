package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLData;
import ru.netology.data.UserInfo;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLData.getVerificationCode;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clearUp() {
        SQLData.clearDb();
    }

    @Test
    void shouldInvalidLogin() {
        val loginPage = new LoginPage();
        val invalidAuth = getInvalidLogin();
        loginPage.login(invalidAuth);
        loginPage.invalidAuth();
    }

    @Test
    void shouldSuccessAuth() {
        val loginPage = new LoginPage();
        val validUser = generateUserInfo();
        val verificationPage = loginPage.validData(validUser);
        val dashboardPage = verificationPage.validVerify(SQLData.getVerificationCode());
        dashboardPage.dashboardPageVisible();
    }

    @Test
    void shouldInvalidPassword() {
        val loginPage = new LoginPage();
        val invalidAuth = getInvalidPassword();
        loginPage.login(invalidAuth);
        loginPage.invalidAuth();
    }

    @Test
    void should3InputWrongPassword() {
        val invalidUser = getInvalidPassword();
        val loginPage = new LoginPage();
        loginPage.login(invalidUser);
        loginPage.invalidAuth();
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.invalidAuth();
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.systemIsLocked();
    }
}
