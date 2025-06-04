package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonSearchTest {
    public static void main(String[] args) {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Create instance of WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Maximize the browser window
            driver.manage().window().maximize();

            // Open Amazon India
            driver.get("https://www.amazon.in/");
            System.out.println("Title of the page: " + driver.getTitle());

            // Locate the search box and enter a non-existing product
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("ld345tsxslfer");

            // Click the search button
            WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
            searchButton.click();

            // Wait for the result page to load
            Thread.sleep(3000);

            // Check for "No results found" or similar text
            WebElement noResultsMessage = driver.findElement(By.cssSelector("span.a-color-state"));

            if (noResultsMessage != null && noResultsMessage.getText().contains("ld345tsxslfer")) {
                System.out.println("Search for non-existing product performed.");
                System.out.println("Displayed message: " + noResultsMessage.getText());

                // Now try to detect any "No results" content
                WebElement noResultsText = driver.findElement(By.xpath("//span[contains(text(),'did not match any products.')]"));

                if (noResultsText != null) {
                    System.out.println("✅ Test Passed: 'No results found' message displayed.");
                } else {
                    System.out.println("❌ Test Failed: 'No results found' message not displayed.");
                }
            } else {
                System.out.println("❌ Test Failed: Unexpected result or search text not found.");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
        } finally {
            // Close browser after short delay
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}
