import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class GooglePlayAutomation {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;


    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus 6T");
        caps.setCapability("udid", "f4b6f2ec"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("skipUnlock","true");
        caps.setCapability("appPackage", "com.android.vending");
        caps.setCapability("appActivity","com.google.android.finsky.activities.MainActivity");
        caps.setCapability("noReset","true");

        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 60);

    }


    @Test
    public void basicTest () throws InterruptedException {

        Thread.sleep(2000);
        MobileElement categories = driver.findElement(MobileBy.xpath("//android.widget.HorizontalScrollView[@text = 'For you']"));
        MobileElement topCharts = driver.findElement(MobileBy.xpath("//android.widget.HorizontalScrollView[@text = 'Kids']"));
        scrollUsingTouchActions_ByElements(categories, topCharts);

    }


    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    //Generic function for Scroll
    public void scrollUsingTouchActions_ByElements(MobileElement startElement, MobileElement endElement) {
        TouchAction actions = new TouchAction(driver);
        actions.press(ElementOption.element(startElement)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(ElementOption.element(endElement)).release().perform();


    }
}

