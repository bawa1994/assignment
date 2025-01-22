import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EbayAddToCartTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Komal Gusain\\IdeaProjects\\assignment\\chromedriver-win64\\chromedriver.exe");


        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get("https://www.ebay.com");
        driver.manage().window().maximize();


        try {



            WebElement searchBox = driver.findElement(By.id("gh-ac"));
            searchBox.sendKeys("book");
            WebElement searchButton = driver.findElement(By.className("gh-search-button__label"));
            searchButton.click();


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Use Duration.ofSeconds()
            WebElement firstBook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[4]/div[3]/div[1]/div[3]/ul/li[1]/div/div[1]/div/a/div/img")));
            firstBook.click();

            driver.switchTo().window(driver.getWindowHandles().stream().skip(1).findFirst().orElse(null));

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"atcBtn_btn_1\"]/span/span")));
            addToCartButton.click();





           // WebElement checkoutasGuest = wait.until(ExpectedConditions.elementToBeClickable(By.className("ux-call-to-action__text")));
            //heckoutasGuest.click();

            WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("gh-cart__icon")));
            WebElement cartCount = driver.findElement(By.className("badge"));


            String cartItemCount = cartCount.getText();
            if (!cartItemCount.equals("1")) {
                System.out.println("Test failed: Cart does not show 1 item.");
            } else {
                System.out.println("Test passed: Cart shows 1 item.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            driver.quit();
        }
    }
}