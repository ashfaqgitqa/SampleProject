package com.DataDrivenTesting.Utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SauceDemoLoginTest 
{
	 WebDriver driver;

	    @BeforeClass
	    public void setUp() 
	    {
	        // Set the path to the WebDriver executable
	        WebDriverManager.chromedriver().setup();
	       
	        driver = new ChromeDriver();

	        driver.get("https://www.saucedemo.com/");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    }

	    @DataProvider(name = "loginData")
	    public Object[][] getLoginData() {
	        String filePath = System.getProperty("user.dir")+ "\\TestData\\ddtdata.xlsx";
	        String sheetName = "Sheet1";
	        List<Object[]> testData = ExcelUtils.getTestData(filePath, sheetName);
	        return testData.toArray(new Object[0][]);
	    }

	    @Test(dataProvider = "loginData")
	    public void testLogin(String username, String password, boolean isValid) {
	        WebElement usernameField = driver.findElement(By.id("user-name"));
	        WebElement passwordField = driver.findElement(By.id("password"));
	        WebElement loginButton = driver.findElement(By.id("login-button"));

	        usernameField.clear();
	        usernameField.sendKeys(username);
	        passwordField.clear();
	        passwordField.sendKeys(password);
	        loginButton.click();

	        if (isValid) {
	            Assert.assertTrue(driver.findElement(By.className("title")).isDisplayed(), "Login should be successful");
	        } else {
	            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
	            Assert.assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for invalid login");
	        }

	        // Navigate back to the login page for the next test
	        driver.navigate().back();
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
}
