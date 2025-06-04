package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class AmazonAddProductAndUpdateQuantityTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.get("https://www.amazon.in/");
            System.out.println("Opened Amazon.in");

            // Search for Laptop
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("Laptop");
            driver.findElement(By.id("nav-search-submit-button")).click();
            Thread.sleep(3000);

            // Get list of products on results page
            List<WebElement> products = driver.findElements(By.cssSelector("div.s-main-slot div[data-component-type='s-search-result'] h2 a"));

            if (products.size() >= 1) {
                // Click on the first product
                WebElement firstProduct = products.get(0);
                String productTitle = firstProduct.getText();
                firstProduct.click();

                // Switch to new tab (if opened)
                for (String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                }

                // Add to cart
                WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-button"));
                addToCartBtn.click();
                Thread.sleep(3000);

                // Go to cart
                driver.findElement(By.id("nav-cart")).click();
                Thread.sleep(3000);

                // Update quantity to 2
                WebElement quantityDropdown = driver.findElement(By.name("quantity"));
                quantityDropdown.click();
                Thread.sleep(1000);
                WebElement qtyTwo = driver.findElement(By.xpath("//option[@value='2']"));
                qtyTwo.click();
                Thread.sleep(3000); // wait for cart to update

                // Verify quantity = 2
                WebElement quantityElement = driver.findElement(By.name("quantity"));
                String quantity = quantityElement.getAttribute("value");

                // Get price element
                WebElement priceElement = driver.findElement(By.cssSelector("span.a-size-medium.a-color-base.sc-price"));

                System.out.println("Product: " + productTitle);
                System.out.println("Quantity in cart: " + quantity);
                System.out.println("Price in cart: " + priceElement.getText());

                if ("2".equals(quantity)) {
                    System.out.println("✅ Test Passed: Quantity updated to 2");
                } else {
                    System.out.println("❌ Test Failed: Quantity not updated correctly");
                }

            } else {
                System.out.println("❌ No products found in search results.");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
        } finally {
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
            driver.quit();
        }
    }
}
