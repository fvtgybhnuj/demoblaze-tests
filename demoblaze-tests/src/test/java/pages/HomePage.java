package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "loginusername")
    private WebElement usernameField;

    @FindBy(id = "loginpassword")
    private WebElement passwordField;

    @FindBy(css = "button[onclick='logIn()']")
    private WebElement loginButton;

    @FindBy(id = "nameofuser")
    private WebElement welcomeText;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openLoginModal() {
        loginLink.click();
    }

    public void login(String username, String password) {
        openLoginModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public String getWelcomeText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(welcomeText));
        return welcomeText.getText();
    }

    public void openProduct(String productName) {
        driver.findElement(By.linkText(productName)).click();
    }

    public void goToCart() {
        cartLink.click();
    }
}