package demo;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class OrangeHRM {

	public WebDriver driver;
	public String parentId;

	@Parameters("Browser")
	@BeforeTest
	public void setup(String browserName ) {

		System.out.println("browser name is here: " + browserName);
		if(browserName.equals("chrome")) {
			driver = new ChromeDriver();
		}else if(browserName.equals("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	@Test(priority = 1)
	public void loginOrangeHRM() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[text()=' Login ']")).click();

		Assert.assertEquals(driver.getTitle(), "OrangeHRM");

	}
	
	
	@Test(priority = 2)
	public void helpOrangeHRM() {

		parentId = driver.getWindowHandle();
		driver.findElement(By.xpath("//button[@title='Help']")).click();
		Set<String> ids = driver.getWindowHandles();
		for(String id : ids) {
			if(!parentId.equalsIgnoreCase(id)) {
				driver.switchTo().window(id);
			}
		}
		
        WebElement search =driver.findElement(By.xpath("//input[@id='query']"));
		Assert.assertNotNull(search);
	}

	@Test(priority = 3)
	public void logoutOrangeHRM() {

		driver.switchTo().window(parentId);
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	@AfterTest
	public void teardown() {

		
		driver.close();
		driver.quit();
	}

}
