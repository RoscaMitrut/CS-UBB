package demo.steps.serenity;

import demo.pages.HomePage;
import net.thucydides.core.annotations.Step;

import java.util.List;

public class HomeSteps {
	HomePage homePage;

	@Step
	public void selectSortingOption(String value) {
		homePage.selectSortingOption(value);
	}

	@Step
	public List<Double> getPricesList() {
		return homePage.getPricesList();
	}

	@Step
	public Integer getCurrentItemsInCartCount() {
		return homePage.getCurrentItemsInCartCount();
	}

	@Step
	public void addRandomToCart() {
		homePage.addToCart();
	}

	@Step
	public void logout() {
		homePage.logout();
	}
}
