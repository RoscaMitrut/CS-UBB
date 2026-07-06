package demo.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@DefaultUrl("https://www.saucedemo.com/inventory.html")
public class HomePage extends PageObject {

	@FindBy(className="product_sort_container")
	private WebElementFacade productSortSelect;

	@FindBy(className="inventory_list")
	private WebElementFacade inventoryList;

	@FindBy(className="shopping_cart_link")
	private WebElementFacade shoppingCartLink;

	@FindBy(id="react-burger-menu-btn")
	private WebElementFacade menuBtn;

	@FindBy(id="menu_button_container")
	private WebElementFacade menuContainer;

	public void selectSortingOption(String value) {
		productSortSelect.selectByValue(value);
	}

	public List<Double> getPricesList(){
		return inventoryList.findElements(By.className("inventory_item")).stream()
				.map(listItem -> {
							WebElement description = listItem.findElement(By.className("inventory_item_description"));
							WebElement priceBar = description.findElement(By.className("inventory_item_price"));
							return Double.parseDouble(priceBar.getText().substring(1));
						}
				)
				.collect(Collectors.toList());
	}

	public Integer getCurrentItemsInCartCount(){
		try {
			WebElement itemsSpan = shoppingCartLink.findElement(By.className("shopping_cart_badge"));
			return Integer.parseInt(itemsSpan.getText());
		} catch (NoSuchElementException ex) {
			return 0;
		}
	}

	public void addToCart() {
		WebElement priceBar = inventoryList.findElement(By.className("pricebar"));
		WebElement addBtn = priceBar.findElement(By.tagName("button"));
		addBtn.click();
	}

//	public void addToCart() {
//		List<WebElement> addButtons = getDriver().findElements(By.xpath("//button[contains(text(),'Add to cart')]"));
//		if (!addButtons.isEmpty()) {
//			Random rand = new Random();
//			addButtons.get(rand.nextInt(addButtons.size())).click();
//		} else {
//			System.out.println("** No add to cart buttons found!");
//		}
//	}


	public void logout() {
		menuBtn.click();
		WebElement logoutBtn = menuContainer.findElement(By.id("logout_sidebar_link"));
		logoutBtn.click();
	}
}
