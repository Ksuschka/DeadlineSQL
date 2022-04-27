package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    public DataHelper() {
    }

    public static UserInfo generateUserInfo() {
        String login = "vasya";
        String password = "qwerty123";
        return new UserInfo("vasya", "qwerty123");
    }

    public static UserInfo getInvalidLogin() {
        Faker faker = new Faker();
        return new UserInfo(faker.name().firstName(), "qwerty123");
    }

    public static UserInfo getInvalidPassword() {
        Faker faker = new Faker();
        return new UserInfo("vasya", faker.internet().password());
    }
}
