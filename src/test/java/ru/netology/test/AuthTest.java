package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLData;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLData.getVerificationCode;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        SQLData.cleanDb();
    }


    @Test
    void shouldValidAuth() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val dashboardPage = verificationPage.validVerify("12345");
        dashboardPage.dashboardPageVisible();
    }

    @Test
    void shouldInvalidLogin() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInvalidLogin();
        loginPage.validAuth(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    void shouldInvalidPassword() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInvalidPassword();
        loginPage.validAuth(authInfo);
        loginPage.invalidAuth();
    }

    @Test
    void shouldInvalidCode() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = getInvalidVerificationCode();
        verificationPage.validVerify(verificationCode);
        verificationPage.invalidVerify();
    }

    @Test
    void should3InputWrongPassword() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInvalidPassword();
        loginPage.validAuth(authInfo);
        loginPage.invalidAuth();
        loginPage.cleanFields();
        loginPage.invalidAuth();
        loginPage.cleanFields();
        loginPage.cleanFields();
        loginPage.invalidAuth();
    }
}
