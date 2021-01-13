import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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

public class ScrollingInContactsApp {
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
        caps.setCapability("appPackage", "com.android.dialer");
        caps.setCapability("appActivity","com.android.dialer.DialtactsActivity");


        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 60);

    }


    @Test
    public void basicTest () throws InterruptedException {

        //WOrking with calculator app
        Thread.sleep(2000);
        driver.findElementById("android:id/button1").click();
        Thread.sleep(2000);
        driver.findElementById("com.android.permissioncontroller:id/permission_allow_button").click();
        Thread.sleep(2000);
        MobileElement el1 = (MobileElement) driver.findElementByAccessibilityId("Contacts tab.");
        el1.click();
        Thread.sleep(2000);



       String text = "Adobe Bridge";
//
//        driver. findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.android.dialer:id/contact_item\")).scrollIntoView("
//                + "new UiSelector().text(\""+text+"\"));");

        driver.findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + text + "\").instance(0))").click();

    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    public boolean verticalScroll() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "down");
            js.executeScript("mobile: scroll", scrollObject);
            System.out.println("Swipe up was Successfully done.");
        }
        catch (Exception e)
        {
            System.out.println("swipe up was not successfull");
        }
        return false;

    }
}
