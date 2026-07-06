package demo.features.search;

import demo.steps.serenity.LoginSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
public class LoginStory {
	@Managed(uniqueSession = true, driver = "chrome")
	public WebDriver webdriver;

	@Steps
	public LoginSteps loginSteps;

	@Test
	public void loginValidTest(){
		loginSteps.openLoginPage();
		loginSteps.enterCredentials("standard_user","secret_sauce");
		loginSteps.clickLoginButton();
		String currentUrl = webdriver.getCurrentUrl();
		assertEquals("https://www.saucedemo.com/inventory.html", currentUrl);
	}
	@Test
	public void loginNotValidTest(){
		loginSteps.openLoginPage();
		loginSteps.enterCredentials("standard_userXXX","secret_sauce");
		loginSteps.clickLoginButton();
		String errorMessage = loginSteps.getErrorMessage();
		System.out.println("** " + errorMessage);
		assertEquals("Epic sadface: Username and password do not match any user in this service", errorMessage);
	}
}
