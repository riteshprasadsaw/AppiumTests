import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Android_NativeApp_Calculator {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;


    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus 6T");
        caps.setCapability("udid", "f4b6f2ec"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("skipUnlock","true");
        caps.setCapability("appPackage", "com.oneplus.calculator");
        caps.setCapability("appActivity","com.oneplus.calculator.Calculator");
        caps.setCapability("noReset","false");
        caps.setCapability("unlockType","pin");
        caps.setCapability("unlockKey","781414");

        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 60);

    }


    @Test
    public void basicTest () throws InterruptedException {

        //WOrking with calculator app
        Thread.sleep(5000);
        driver.findElementById("com.oneplus.calculator:id/digit_7").click();
        driver.findElementById("com.oneplus.calculator:id/op_add").click();
        driver.findElementById("com.oneplus.calculator:id/digit_6").click();
        driver.findElementById("com.oneplus.calculator:id/eq").click();
        String result= driver.findElementById("com.oneplus.calculator:id/result").getText();
        System.out.println("Result is : "+result);

        Map<String, Object> res = (Map<String, Object>)driver.executeScript("mobile: getNotifications");
        List<Map<String, Object>> notifications = (List<Map<String, Object>>)res.get("statusBarNotifications");
        for (Map<String, Object> notification : notifications) {
            Map<String, String> innerNotification = (Map<String, String>)notification.get("notification");
            if (innerNotification.get("bigTitle") != null) {
                System.out.println(innerNotification.get("bigTitle"));
            } else {
                System.out.println(innerNotification.get("title"));
            }
            if (innerNotification.get("bigText") != null) {
                System.out.println(innerNotification.get("bigText"));
            } else {
                System.out.println(innerNotification.get("text"));
            }
        }


    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
