import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AppiumStartProgramtically{

    private AppiumDriver driver;
    private static AppiumDriverLocalService server;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        // Use any port, in case the default 4723 is already taken (maybe by another Appium server)
        serviceBuilder.usingAnyFreePort();
        // Tell serviceBuilder where node is installed. Or set this path in an environment variable named NODE_PATH
        serviceBuilder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
        // Tell serviceBuilder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH
        serviceBuilder.withAppiumJS(new File("C:\\Users\\Ritesh\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Appium.exe"));
        // The XCUITest driver requires that a path to the Carthage binary is in the PATH variable. I have this set for my shell, but the Java process does not see it. It can be inserted here.
        HashMap<String, String> environment = new HashMap();
        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
        serviceBuilder.withEnvironment(environment);

        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "f4b6f2ec");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        driver = new AndroidDriver<>(server.getUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void Sum() {
        System.out.println("Calculate sum of two numbers");
        // Locate elements to enter data and click +/= buttons
        driver.findElement(By.xpath("//*[@text='1']")).click();
        driver.findElement(By.xpath("//*[@text='+']")).click();
        driver.findElement(By.xpath("//*[@text='2']")).click();
        driver.findElement(By.xpath("//*[@content-desc='equals']")).click();

        // Get the result text
        WebElement sumOfNumbersEle = driver.findElement(By.className("android.widget.EditText"));
        String sumOfNumbers = sumOfNumbersEle.getText();

        // verify if result is 3
        Assert.assertTrue(sumOfNumbers.endsWith("3"));
    }

    @AfterTest
    public void End() {
        System.out.println("Stop driver");
        driver.quit();
        System.out.println("Stop appium service");
        server.stop();
    }

}