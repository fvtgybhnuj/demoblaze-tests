package tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;

import java.time.Duration;

@Epic("Demoblaze E-commerce")
@Feature("Основные пользовательские сценарии")
public class DemoblazeTests extends BaseTest {

    private static final String BASE_URL = "https://demoblaze.com/";
    private static final String USERNAME = "mikhail2026";
    private static final String PASSWORD = "Password123";

    @Test(priority = 1, description = "Сквозной сценарий: Авторизация, проверка пустой корзины и покупка")
    @Story("Авторизация и покупка")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginAndAddProductsToCart() {
        HomePage homePage = new HomePage(driver);

        Allure.step("Открыть главную страницу Demoblaze");
        homePage.navigateTo(BASE_URL);

        Allure.step("Войти с учетными данными: " + USERNAME);
        homePage.login(USERNAME, PASSWORD);

        Allure.step("Проверить приветственное сообщение");
        String welcome = homePage.getWelcomeText();
        Assert.assertTrue(welcome.contains("Welcome"),
                "Ожидалось приветствие, получено: " + welcome);

        Allure.step("Проверка и очистка корзины перед тестом");
        homePage.goToCart();
        clearCart(homePage);

        Allure.step("Открыть страницу товара 'Samsung galaxy s6'");
        homePage.navigateTo(BASE_URL);
        homePage.openProduct("Samsung galaxy s6");

        Allure.step("Нажать 'Add to cart' и подтвердить alert");
        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();

        Allure.step("В боковом меню выбрать категорию 'Monitors'");
        homePage.navigateTo(BASE_URL);
        homePage.clickCategory("Monitors");

        Allure.step("Открыть товар 'Apple monitor 24'");
        homePage.openProduct("Apple monitor 24");

        Allure.step("Нажать 'Add to cart' и подтвердить alert");
        productPage.addToCart();

        Allure.step("Перейти в корзину");
        homePage.goToCart();

        Allure.step("Проверить, что в корзине ровно 2 добавленных товара");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int cartItems = driver.findElements(By.cssSelector("tr.success")).size();
        Assert.assertEquals(cartItems, 2, "Ожидалось 2 товара в корзине, но найдено: " + cartItems);

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Samsung galaxy s6"), "Телефон не найден в корзине");
        Assert.assertTrue(pageSource.contains("Apple monitor 24"), "Монитор не найден в корзине");
    }

    @Test(priority = 2, description = "Переход в контакты и отправка сообщения")
    @Story("Контакты")
    @Severity(SeverityLevel.MINOR)
    public void testContactForm() {
        HomePage homePage = new HomePage(driver);

        Allure.step("Открыть главную страницу Demoblaze");
        homePage.navigateTo(BASE_URL);

        Allure.step("Нажать на ссылку 'Contact' в верхнем меню");
        homePage.goToContact();

        Allure.step("Заполнить поля формы обратной связи");
        driver.findElement(By.id("recipient-email")).sendKeys("student@example.com");
        driver.findElement(By.id("recipient-name")).sendKeys("Mikhail");
        driver.findElement(By.id("message-text")).sendKeys("Hello! This is a test message.");

        Allure.step("Нажать кнопку Send message");
        driver.findElement(By.xpath("//button[text()='Send message']")).click();

        Allure.step("Подтвердить всплывающее окно (Alert)");
        try {
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            System.out.println("Alert не появился");
        }
    }


    private void clearCart(HomePage homePage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.success")));
        } catch (Exception e) {
            return;
        }

        while (!driver.findElements(By.linkText("Delete")).isEmpty()) {
            try {
                int initialRowsCount = driver.findElements(By.cssSelector("tr.success")).size();

                WebElement firstDeleteButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
                firstDeleteButton.click();

                wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("tr.success"), initialRowsCount));
            } catch (Exception e) {
            }
        }
    }
}