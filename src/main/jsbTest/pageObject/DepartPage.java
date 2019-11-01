import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DepartPage {

    AndroidDriver driver;
    WebElement element;

    public DepartPage(AndroidDriver driver){
        this.driver = driver;
    }

    //
    public WebElement getSendAll(){
        element = driver.findElement(By.id("btn_send_all"));
        return element;
    }
}
