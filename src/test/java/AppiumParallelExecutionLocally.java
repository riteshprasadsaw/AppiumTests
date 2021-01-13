import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
public class AppiumParallelExecutionLocally implements Runnable{

    String port;
    String udid;

    public AppiumParallelExecutionLocally (String portNumber, String udid) {
        this.port = portNumber;
        this.udid = udid;
    }

    @AndroidFindBy(id="in.amazon.mShop.android.shopping:id/sso_continue")
    private WebElement continueButton;

    @AndroidFindBy(id="in.amazon.mShop.android.shopping:id/rs_search_src_text")
    private WebElement searchBox;

    AppiumDriver<WebElement> driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();


    private void openAppAndPerformSomeActions() throws InterruptedException {
        capabilities.setCapability("deviceName", "OnePlus 6T");
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("skipUnlock","true");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
        capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");

        try {
            driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:" + port + "/wd/hub"), capabilities);
            Thread.sleep(10000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

        continueButton.click();
        searchBox.click();
        searchBox.sendKeys("IPad");
        Thread.sleep(3000);
    }

    public static void main(String args[]) {
        Runnable r1 = new AppiumParallelExecutionLocally("4723", "f4b6f2ec"); //device id of first mobile device
        //Runnable r2 = new AppiumParallelExecutionLocally("4723", "BDE3N1678E001068"); //device id of second mobile device
        new Thread(r1).start();
        //new Thread(r2).start();
    }

    @Override
    public void run() {
        try {
            openAppAndPerformSomeActions();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
