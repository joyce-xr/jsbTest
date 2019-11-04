import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DepartPage {

    AndroidDriver driver;
    WebElement element;

    public DepartPage(AndroidDriver driver){
        this.driver = driver;
    }

    //全部发车按钮
    public WebElement getSendAll(){
        element = driver.findElement(By.id("btn_send_all"));
        return element;
    }

    //确认发车按钮
    public WebElement getEnsure(){
        element = driver.findElement(By.id("btn_ensure"));
        return element;
    }

    //返回按钮
    public WebElement getReturnBtn(){
        element = driver.findElement(By.id("img_left_btn"));
        return element;
    }

}
