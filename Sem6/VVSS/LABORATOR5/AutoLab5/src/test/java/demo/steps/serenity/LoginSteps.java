package demo.steps.serenity;

import demo.pages.LoginPage;
import net.thucydides.core.annotations.Step;

public class LoginSteps {
	LoginPage loginPage;

	@Step
	public void openLoginPage() {
		loginPage.open();
	}

	@Step
	public void enterCredentials(String username, String password) {
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
	}

	@Step
	public void clickLoginButton() {
		loginPage.clickLoginButton();
	}

	@Step
	public String getErrorMessage() {
		return loginPage.getErrorMessage();
	}
}
