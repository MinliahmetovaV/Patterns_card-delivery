package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.unit.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//input[@placeholder='Город']").val(validUser.getCity()).pressTab().pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $x("//input[@name='name']").val(validUser.getName());
        $x("//input[@name='phone']").val(validUser.getPhone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[text()='Запланировать']").click();
        $x("//div[@class='notification__content']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[contains(text(),'Встреча успешно запланирована на ')]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate),visible);
        $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(secondMeetingDate);
        $x("//span[text()='Запланировать']").click();
        $x("//div[contains(text(),'У вас уже запланирована')]").shouldHave(visible, text("У вас уже запланирована"));
        $x("//span[text()='Перепланировать']").click();
        $x("//div[contains(text(),'Успешно!')]").shouldBe(visible,Duration.ofSeconds(15));
        $x("//div[@data-test-id='success-notification']").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), visible);
    }

}
