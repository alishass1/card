package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        faker = new Faker(new Locale("ru"));
    }

    private String generateDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private String generateName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private String generatePhone() {
        return "+7" + faker.number().digits(10);
    }

    private void clearDateField() {
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
    }

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfullySubmitForm() {
        String city = "Москва";
        String name = generateName();
        String phone = generatePhone();
        String date = generateDate(3);

        $("[data-test-id='city'] input").setValue(city);
        clearDateField();
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();

        $(".notification__content")
                .shouldHave(Condition.text("Успешно!"), Condition.visible);
    }

    @Test
    void shouldSelectCityFromDropdown() {
        String city = "Казань";
        String name = generateName();
        String phone = generatePhone();
        String date = generateDate(7);

        $("[data-test-id='city'] input").setValue("Ка");
        $(".menu-item__control").shouldBe(Condition.visible);
        $$(".menu-item__control").findBy(Condition.text(city)).click();

        clearDateField();
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();

        $(".notification__content")
                .shouldHave(Condition.text("Успешно!"), Condition.visible);
    }

    @Test
    void shouldSelectDateFromCalendar() {
        String city = "Москва";
        String name = generateName();
        String phone = generatePhone();

        LocalDate targetDate = LocalDate.now().plusDays(7);
        String day = String.valueOf(targetDate.getDayOfMonth());
        String monthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        $("[data-test-id='city'] input").setValue(city);

        $("[data-test-id='date'] input").click();

        while (!$(".calendar__month").getText().equals(monthYear)) {
            $("[data-step='1']").click();
        }

        $$(".calendar__day").findBy(Condition.text(day)).click();

        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();

        $(".notification__content")
                .shouldHave(Condition.text("Успешно!"), Condition.visible);
    }

    @Test
    void shouldSelectCityAndDateFromDropdownAndCalendar() {
        String city = "Екатеринбург";
        String name = generateName();
        String phone = generatePhone();

        LocalDate targetDate = LocalDate.now().plusDays(14);
        String day = String.valueOf(targetDate.getDayOfMonth());
        String monthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        $("[data-test-id='city'] input").setValue("Ек");
        $(".menu-item__control").shouldBe(Condition.visible);
        $$(".menu-item__control").findBy(Condition.text(city)).click();

        $("[data-test-id='date'] input").click();

        while (!$(".calendar__month").getText().equals(monthYear)) {
            $("[data-step='1']").click();
        }

        $$(".calendar__day").findBy(Condition.text(day)).click();

        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();

        $(".notification__content")
                .shouldHave(Condition.text("Успешно!"), Condition.visible);
    }
}