package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

        private SelenideElement loginField = $("[data-test-id=login] input");
        private SelenideElement passwordField = $("[data-test-id=password] input");
        private SelenideElement loginButton = $("[data-test-id=action-login]");
        private SelenideElement errorNotification = $("[data-test-id='error-notification']");


        public void correctData(DataHelper.AuthInfo info) {
                loginField.setValue(info.getLogin());
                passwordField.setValue(info.getPassword());
                loginButton.click();
        }

        public VerificationPage validAuth(DataHelper.AuthInfo info) {
                correctData(info);
                return new VerificationPage();
        }

        public void invalidAuth() {
                errorNotification.shouldBe(Condition.visible);
        }


        public void cleanFields() {
                loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
                passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        }
}
