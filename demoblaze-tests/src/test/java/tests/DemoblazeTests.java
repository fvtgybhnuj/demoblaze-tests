package tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;

@Epic("Demoblaze E-commerce")
@Feature("Основные пользовательские сценарии")
public class DemoblazeTests extends BaseTest {

    private static final String BASE_URL = "https://demoblaze.com/";

    @Test(description = "Успешный вход в систему")
    @Story("Авторизация")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяет, что пользователь может войти с валидными учетными данными")
    public void testSuccessfulLogin() {
        HomePage homePage = new HomePage(driver);

        Allure.step("Открыть главную страницу Demoblaze");
        homePage.navigateTo(BASE_URL);

        Allure.step("Войти с учетными данными: testuser / testpassword");
        homePage.login("mikhail2026", "Password123");

        Allure.step("Проверить приветственное сообщение");
        String welcome = homePage.getWelcomeText();
        Assert.assertTrue(welcome.contains("Welcome"),
                "Ожидалось приветствие, получено: " + welcome);
    }

    @Test(description = "Добавление товара в корзину")
    @Story("Корзина")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяет, что товар Samsung galaxy s6 добавляется в корзину")
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(driver);

        Allure.step("Открыть главную страницу Demoblaze");
        homePage.navigateTo(BASE_URL);

        Allure.step("Открыть страницу товара 'Samsung galaxy s6'");
        homePage.openProduct("Samsung galaxy s6");

        Allure.step("Нажать 'Add to cart' и подтвердить alert");
        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();

        Allure.step("Перейти в корзину");
        homePage.goToCart();

        Allure.step("Проверить, что корзина содержит товар");
        int cartItems = driver.findElements(By.cssSelector("tr.success")).size();
        Assert.assertTrue(cartItems > 0,
                "Корзина пуста после добавления товара");
    }
}