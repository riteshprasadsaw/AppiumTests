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

public class ioSampleTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    //Elements
    String secondNewJob = "//android.widget.FrameLayout[2]/android.widget.LinearLayout/" +
            "android.widget.RelativeLayout/android.widget.ImageView";

    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus 6T");
        caps.setCapability("udid", "f4b6f2ec"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10");
        caps.setCapability("skipUnlock","true");
        caps.setCapability("appPackage", "com.isinolsun.app");
        caps.setCapability("appActivity","com.isinolsun.app.activities.SplashActivity");
        caps.setCapability("noReset","false");
        caps.setCapability("automationName","uiautomator2");
        caps.setCapability("app","E:\\appium-sample-test\\apk\\isinolsun-v2.1.7.apk");
        caps.setCapability("unlockType","pin");
        caps.setCapability("unlockKey","781414");

        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
        wait = new WebDriverWait(driver, 60);

    }


    @Test
    public void basicTest () throws InterruptedException {
        //Click and pass Splash
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.isinolsun.app:id/animation_view"))).click();

        //Click I am searching a job
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.isinolsun.app:id/bluecollar_type_button"))).click();


        //Notification Allow
        if (driver.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).size()>0) {
            driver.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).get(0).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(secondNewJob)));

    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}
