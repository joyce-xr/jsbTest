import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PickPage {

    AndroidDriver driver;
    WebElement element;

    //搜索：单号输入框
    public WebElement getBillNoSearch(){
        element = driver.findElement(By.id("edt_search"));
        return element;
    }

    //搜索按钮
    public WebElement getSearchBtn(){
        element = driver.findElement(By.id("btn_search"));
        return element;
    }

    //运单详情入口
    public WebElement getBillInfo(){
        element = driver.findElement(By.id("iv_right"));
        return element;
    }

    //付货按钮
    public WebElement getPickBtn(){
        element = driver.findElement(By.id("btn_pay_goods"));
        return element;
    }

    //确认付货按钮
    public WebElement getEnsureBtn(){
        element = driver.findElement(By.id("tv_sure"));
        return element;
    }
}
