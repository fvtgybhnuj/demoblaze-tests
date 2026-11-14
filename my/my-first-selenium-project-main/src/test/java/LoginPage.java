import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {
    public SelenideElement usernameInput = $("#username");
    public SelenideElement passwordInput = $("#password");
    public SelenideElement loginButton = $("#loginButton");
    public SelenideElement errorMessage = $("#message");

    public LoginPage openPage() {
        com.codeborne.selenide.Selenide.open("https://slqa.ru/cases/ChatGPTLogin/");
        return this;
    }

    public void login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public SelenideElement getErrorMessage() {
        return errorMessage;
    }
}