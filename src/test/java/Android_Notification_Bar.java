import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Android_Notification_Bar {

    private String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.10.0/TheApp-v1.10.0.apk";
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;


    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus 6T");
        caps.setCapability("udid", "f4b6f2ec"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("app", APP);
        caps.setCapability("skipUnlock","true");


        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 60);

    }


    @Test
    public void basicTest () throws InterruptedException {

        //WOrking with calculator app
        Thread.sleep(5000);

        driver.openNotifications();
        List<MobileElement> elements = driver.findElementsById("com.android.systemui:id/notification_title");

        for(MobileElement element:elements){
            System.out.println("Notification is: "+element.getText());
        }

    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
