import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("Тестовый набор FouthLoginTests - использование логики в автотестах")
public class FouthLoginTests {

    @BeforeAll
    static void before_all(TestInfo test_info) {
        System.out.println(test_info.getDisplayName() + " - начали выполнение.");
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
    }

    @AfterAll
    static void after_all(TestInfo test_info) {
        System.out.println(test_info.getDisplayName() + " - закончили выполнение.");
    }

    @BeforeEach
    void before_each(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - начали выполнение.");
        open("https://slqa.ru/cases/ChatGPTLogin/");
    }

    @AfterEach
    void after_each(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - закончили выполнение.\n");
    }

    void success_login(String username) {
        $("#username").setValue(username);
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));

        $("#greeting").shouldHave(text("Welcome, " + username + "!"));
        $("#greeting").shouldBe(visible);

        $("#logoutButton").click();
    }

    @Test
    @DisplayName("01. Корректные логин и пароль - успешный вход в систему по нажатию кнопки")
    void test01_success_login_button() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
    }

    @Test
    @DisplayName("02. Корректный логин, пароль не соответствует логину - ошибка")
    void test02_error_wrong_password() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("wrong_password");
        $("#loginButton").click();
        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("03. Корректные логин и пароль - успешный вход в систему по нажатию клавиши Enter на клавиатуре")
    void test03_success_login_enter() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#password").pressEnter();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
    }

    @Test
    @DisplayName("04. Выход из системы")
    void test04_logout_success() {
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#logoutButton").click();
        $("#logoutButton").shouldNotBe(visible);
        $("#loginContainer").shouldBe(visible);
        $("#loginInfo").shouldBe(visible);
        $("#username").shouldBe(visible);
        $("#password").shouldBe(visible);
        $("#loginButton").shouldBe(visible);
    }

    @Test
    @DisplayName("05. Некорректный логин, пароль от корректного логина - ошибка")
    void test05_wrong_login_correct_password() {
        $("#username").sendKeys("incorrect_login");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("07. Проверить, что под заблокированным пользователем нельзя войти в систему")
    void test07_error_blocked_user() {
        $("#username").sendKeys("locked_out_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Пользователь заблокирован."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("08. Пустой логин, пароль от корректного логина")
    void test08_empty_login_correct_password() {
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Username is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("09. Пустой пароль, корректный логин")
    void test09_error_empty_password() {
        $("#username").sendKeys("standard_user");
        $("#loginButton").click();
        $("#message").shouldHave(text("Password is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @Test
    @DisplayName("10. Пустые логин и пароль")
    void test10_error_empty_login_and_password() {
        $("#loginButton").click();
        $("#message").shouldHave(text("Username and Password are required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));
        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    @ParameterizedTest
    @DisplayName("12. Проверить вход в систему под несколькими разными логинами")
    void test12_success_logins_different_users() {
        success_login("standard_user");
        success_login("problem_user");
        success_login("performance_glitch_user");
        success_login("error_user");
        success_login("visual_user");
    }
}