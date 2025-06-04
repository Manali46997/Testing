package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonRemoveProductFromCartTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.get("https://www.amazon.in/");
            
            // Click on the Cart icon to open the cart page
            WebElement cartIcon = driver.findElement(By.id("nav-cart"));
            cartIcon.click();

            Thread.sleep(3000);  // Wait for cart page to load
            
            // Check if the cart is empty or has items
            boolean cartIsEmpty = false;
            try {
                // This element appears if the cart is empty
                WebElement emptyCartMsg = driver.findElement(By.xpath("//div[contains(text(),'Your Amazon Basket is empty')]"));
                if (emptyCartMsg.isDisplayed()) {
                    cartIsEmpty = true;
                }
            } catch (Exception e) {
                // If no empty message found, cart likely has items
                cartIsEmpty = false;
            }

            if (cartIsEmpty) {
                System.out.println("Cart is already empty. No items to remove.");
            } else {
                // Remove all products by clicking 'Delete' or 'Remove' buttons
                // Amazon typically uses this data-action for removing: input[value='Delete']
                // Or button with aria-label containing 'Delete'
                // Let's try to find and click all remove buttons:

                // Find all remove buttons (there may be more than one)
                java.util.List<WebElement> removeButtons = driver.findElements(By.xpath("//input[@value='Delete' or @aria-label='Delete']"));

                if (removeButtons.isEmpty()) {
                    // Alternative XPath for remove buttons
                    removeButtons = driver.findElements(By.xpath("//input[@value='Delete'] | //input[contains(@aria-label,'Delete')]"));
                }

                if (removeButtons.isEmpty()) {
                    System.out.println("No remove/delete buttons found. Possibly cart is empty or layout changed.");
                } else {
                    // Click all remove buttons one by one
                    for (WebElement btn : removeButtons) {
                        btn.click();
                        Thread.sleep(2000); // Wait for item removal
                    }
                    System.out.println("Removed all products from cart.");
                }
                
                // After removal, verify cart is empty
                Thread.sleep(3000);
                WebElement emptyCartMsg = driver.findElement(By.xpath("//div[contains(text(),'Your Amazon Basket is empty')]"));
                if (emptyCartMsg.isDisplayed()) {
                    System.out.println("✅ Test Passed: Cart is empty after removing products.");
                } else {
                    System.out.println("❌ Test Failed: Cart is not empty after removing products.");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
        } finally {
            try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
            driver.quit();
        }
    }
}
