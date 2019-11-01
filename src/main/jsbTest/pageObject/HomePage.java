import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    AndroidDriver driver;
    WebElement element = null;

    public HomePage(AndroidDriver driver){
        this.driver = driver;
    }

    //受理开单
    public WebElement getNewBillIcon() {
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"受理开单\")");
        return element;
    }

    //配载发车
    public WebElement getDepartIcon() {
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"配载发车\")");
        return element;
    }
    //到车签收
    public WebElement getDepartArrivalIcon() {
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"到车签收\")");
        return element;
    }
    //付货
    public WebElement getPickIcon() {
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"付货\")");
        return element;
    }
    //网点回款管理
    public WebElement getNetWorkPayIcon() {
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"网点回款管理\")");
        return element;
    }
}
