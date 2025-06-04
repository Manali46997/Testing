package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class AmazonAddToCartTest {
    public static void main(String[] args) {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Maximize browser window
            driver.manage().window().maximize();

            // Open Amazon India
            driver.get("https://www.amazon.in/");
            System.out.println("Opened Amazon.in");

            // Search for a product (e.g., Laptop)
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("Laptop");
            driver.findElement(By.id("nav-search-submit-button")).click();
            Thread.sleep(3000);

            // Find list of product links (excluding ads and filters)
            List<WebElement> productLinks = driver.findElements(By.cssSelector("div.s-main-slot div[data-component-type='s-search-result'] h2 a"));

            if (productLinks.size() >= 4) {
                // Click on the 4th product (index 3)
                WebElement fourthProduct = productLinks.get(3);
                String productTitle = fourthProduct.getText();
                fourthProduct.click();

                // Switch to new tab
                for (String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                }

                // Get price before adding to cart
                String productPrice = "N/A";
                try {
                    WebElement priceElement = driver.findElement(By.id("priceblock_ourprice"));
                    productPrice = priceElement.getText();
                } catch (Exception e) {
                    try {
                        WebElement dealPrice = driver.findElement(By.id("priceblock_dealprice"));
                        productPrice = dealPrice.getText();
                    } catch (Exception ignore) {
                    }
                }

                // Click "Add to Cart" button
                WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-button"));
                addToCartBtn.click();
                Thread.sleep(3000);

                // Go to cart
                driver.findElement(By.id("nav-cart")).click();
                Thread.sleep(3000);

                // Verify quantity = 1 and price present
                WebElement quantityElement = driver.findElement(By.cssSelector("span.a-dropdown-prompt"));
                WebElement cartPriceElement = driver.findElement(By.cssSelector("span.a-size-medium.a-color-base.sc-price"));

                String quantity = quantityElement.getText();
                String cartPrice = cartPriceElement.getText();

                System.out.println("✅ Product added to cart:");
                System.out.println("Product: " + productTitle);
                System.out.println("Expected Price: " + productPrice);
                System.out.println("Cart Price: " + cartPrice);
                System.out.println("Quantity in cart: " + quantity);

                if (quantity.equals("1")) {
                    System.out.println("✅ Test Passed: Correct quantity");
                } else {
                    System.out.println("❌ Test Failed: Incorrect quantity");
                }

            } else {
                System.out.println("❌ Less than 4 products found in the search results.");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
        } finally {
            // Wait and close the browser
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}
