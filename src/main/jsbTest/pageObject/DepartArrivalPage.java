import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DepartArrivalPage {
    AndroidDriver driver;
    WebElement element;

    //到车签收-按钮-默认只有一个
    public WebElement getSignBtn(){
        element = driver.findElement(By.id("tv_sign"));
        return element;
    }

    //确认签收按钮
    public  WebElement getEnsureBtn(){
        element = driver.findElement(By.id("btn_ensure"));
        return element;
    }

    //返回按钮
    public WebElement getReturnBtn(){
        element = driver.findElement(By.id("rv_left_btn"));
        return element;
    }
}
