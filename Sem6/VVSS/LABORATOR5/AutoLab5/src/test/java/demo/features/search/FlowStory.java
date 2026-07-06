package demo.features.search;

import demo.steps.serenity.HomeSteps;
import demo.steps.serenity.LoginSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
@UseTestDataFrom("src/test/resources/TestData.csv")
public class FlowStory {
	@Managed(uniqueSession = true, driver = "chrome")
	public WebDriver webdriver;

	@Steps
	public LoginSteps loginSteps;

	@Steps
	public HomeSteps homeSteps;

	String username, password;

	@Test
	public void flowTest(){
		// login
		loginSteps.openLoginPage();
		loginSteps.enterCredentials("standard_user","secret_sauce");
		loginSteps.clickLoginButton();
		String currentUrl = webdriver.getCurrentUrl();
		assertEquals("https://www.saucedemo.com/inventory.html", currentUrl);

		// sorting desc by price
		homeSteps.selectSortingOption("hilo");
		List<Double> pricesList = homeSteps.getPricesList();
		List<Double> copy = new ArrayList<>(pricesList);
		pricesList.sort(Comparator.comparing(Double::doubleValue).reversed());
		assertArrayEquals(pricesList.toArray(), copy.toArray());

		// add to cart
		int prevItemCount = homeSteps.getCurrentItemsInCartCount();
		homeSteps.addRandomToCart();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int crtItemCount = homeSteps.getCurrentItemsInCartCount();

		assertEquals(prevItemCount+1, crtItemCount);

		// logout
		homeSteps.logout();
		String urlAfterLogout = webdriver.getCurrentUrl();
		assertEquals("https://www.saucedemo.com/", urlAfterLogout);
	}
}
