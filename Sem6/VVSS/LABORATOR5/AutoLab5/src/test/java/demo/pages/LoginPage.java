package demo.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.saucedemo.com/")
public class LoginPage extends PageObject {

	@FindBy(name="user-name")
	private WebElementFacade userNameTextInput;

	@FindBy(name="password")
	private WebElementFacade passwordTextInput;

	@FindBy(name="login-button")
	private WebElementFacade loginButton;

	public void enterUsername(String keyword) {
		userNameTextInput.type(keyword);
	}
	public void enterPassword(String keyword) {
		passwordTextInput.type(keyword);
	}
	public void clickLoginButton() {
		loginButton.click();
	}
	public String getErrorMessage() {
		WebElementFacade errorContainer = find(By.className("error-message-container"));
		return errorContainer.findElements(By.tagName("h3")).get(0).getText();
	}
}
