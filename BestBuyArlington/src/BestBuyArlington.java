import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BestBuyArlington {

	public static void main(String [] args) throws IOException {
		// Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\jackb\\OneDrive\\Desktop\\Coding Paths\\Browser Drivers\\chromedriver-win32\\chromedriver.exe");
        
        // Set up ChromeOptions to run Chrome in headless mode
        
        /** ChromeOptions options = new ChromeOptions();
        	options.addArguments("--headless");
           options.addArguments("--disable-gpu"); **/

        // Create a new instance of the ChromeDriver
        ChromeDriver driver = new ChromeDriver();

        // Navigate to the Best Buy website
        driver.get("https://www.bestbuy.com/site/sony-playstation-portal-remote-player-white/6562576.p?skuId=6562576");

        // Find the store location button
        WebElement locationButton = driver.findElement(By.cssSelector("button.c-button-unstyled.store-loc-btn"));

        // Click the location button to open the menu
        locationButton.click();
        
        // Find the find other store button
        WebElement findOtherStoreButton = driver.findElement(By.className("find-store-btn"));
        
        // Click the find other store button
        findOtherStoreButton.click();

        // Find and click on the option for a different city CHANGE TO DESIRED CITY
        WebElement cityOption = driver.findElement(By.xpath("//span[text()='Rogers']"));
        cityOption.click();
        
        // Find make this your store button and click it
        WebElement makeThisYourStoreButton = driver.findElement(By.className("make-this-your-store"));
        makeThisYourStoreButton.click();
        
        // Go back to the item page after selecting new store
        driver.navigate().back();
        
        // Wait for any asynchronous operations to complete (adjust as needed)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the updated URL after the location change
        String updatedUrl = driver.getCurrentUrl();
        System.out.println("Updated URL: " + updatedUrl);
		

		String discordWebhookUrl = "https://discord.com/api/webhooks/1179953063298089010/YVL0KnWAizYaf2_CugEmVQ8TJ4g1MvNtowSWL8uOJbYz2n0oXMa-xPO6ygQ1cgpWxok4";
			
			
			try {
	            while (true) {
	            	
	            	// Refresh page before checking if item is in stock
	                driver.navigate().refresh();
	                
	                List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".add-to-cart-button[data-button-state=ADD_TO_CART]"));

	                if (!addToCartButtons.isEmpty() && "Add to Cart".equals(addToCartButtons.get(0).getText())) {
	                    System.out.println("The item is in stock and can be added to the cart!");
	                    sendDiscordNotification(discordWebhookUrl, "This item is in stock and can be added to the cart! " + updatedUrl);
	                } else {
	                    System.out.println("The Add to Cart button is not present or not usable on the webpage.");
	                    // Additional logic if needed when the button is not present or not usable
	                }  

	                Thread.sleep(30000); // checks inventory every 60,000 milliseconds = 30 seconds
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}

	private static void sendDiscordNotification(String webhookUrl, String message) {
	    try {
	        URL url = new URL(webhookUrl);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setRequestProperty("Content-Type", "application/json");
	        httpURLConnection.setDoOutput(true);

	        String jsonInputString = "{\"content\":\"" + message + "\"}";
	        httpURLConnection.getOutputStream().write(jsonInputString.getBytes("UTF-8"));

	        int responseCode = httpURLConnection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
	            System.out.println("Discord notification sent successfully!");
	        } else {
	            System.out.println("Failed to send Discord notification. HTTP Response Code: " + responseCode);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
