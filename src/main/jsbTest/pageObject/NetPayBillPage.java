import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NetPayBillPage {

    AndroidDriver driver;
    WebElement element;

    //今日新增
    public WebElement getTodayNew(){
        element = driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"今日新增\")");
        return element;
    }

    //欠款流水-单号
    public  WebElement getShowSn(){
        element = driver.findElement(By.id("tv_no"));
        return element;
    }

    //欠款流水-运费方式
    public  WebElement getShipPmt(){
        element = driver.findElement(By.id("tv_type"));
        return element;
    }

    //欠款流水-运费金额
    public  WebElement getShipAmt(){
        element = driver.findElement(By.id("tv_amount"));
        return element;
    }

    //今日新增页的返回按钮
    public WebElement getReturnOne(){
        element  = driver.findElement(By.id("img_left_btn"));
        return element;
    }

    //网点回款管理的返回按钮
    public WebElement getReturnTwo(){
        element  = ((WebElement)driver.findElements(By.className("android.widget.ImageView")).get(0));
        return element;
    }

}
