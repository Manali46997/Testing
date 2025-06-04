package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonSearchExistingProductTest {
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

            // Locate search box and enter a valid product name
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("Laptop");

            // Click the search button
            WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
            searchButton.click();

            // Wait for search results to load
            Thread.sleep(3000);

            // Get the page heading or search result indicator
            WebElement resultText = driver.findElement(By.cssSelector("span.a-color-state"));

            // Validate that "Laptop" is in the result
            if (resultText != null && resultText.getText().toLowerCase().contains("laptop")) {
                System.out.println("✅ Test Passed: Product results for 'Laptop' are displayed.");
                System.out.println("Displayed Text: " + resultText.getText());
            } else {
                System.out.println("❌ Test Failed: Expected product results not found.");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception occurred: " + e.getMessage());
        } finally {
            // Close the browser after delay
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}
