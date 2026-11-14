import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SimpleFormTests {
    @Test
    void test01SpecificCommands() {
        open("https://slqa.ru/cases/SimpleForm/");
        sleep(4_000);
        $(By.id("unique_id")).sendKeys("Текст 1");
        sleep(1_000);
        $(By.name("unique_name")).sendKeys("Текст 2");
        sleep(1_000);
        $(By.tagName("blockquote")).shouldBe(Condition.exist);
        $(By.tagName("blockquote")).shouldHave(text("спрашивает"));

        $(By.className("unique_class")).shouldBe(Condition.visible);
        sleep(2_000);
    }

    @Test
    void test02CSSSelectors() {
        open("https://slqa.ru/cases/SimpleForm/");
        $("#unique_id").sendKeys("Текст 1");
        $("[name=unique_name]").sendKeys("Текст 2");
        $("blockquote")
                .shouldBe(Condition.exist)
                .shouldHave(text("спрашивает"));
        $(".unique_class").shouldBe(Condition.visible);
    }
}