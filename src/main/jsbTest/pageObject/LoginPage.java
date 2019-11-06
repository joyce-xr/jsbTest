import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import sun.rmi.runtime.Log;

public class LoginPage {
    AndroidDriver driver;
    WebElement element;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
    }

    public WebElement getSwitchBtn(){
        element = driver.findElement(By.id("btn_switch_login"));
        return element;
    }

    public WebElement getUserName(){
        element = driver.findElement(By.id("edt_name"));
        return element;
    }

    public WebElement getPassWord(){
        element = driver.findElement(By.id("edt_pwd"));
        return element;
    }

    public WebElement getLoginBtn(){
        element = driver.findElement(By.id("btn_login"));
        return element;
    }

    //设置
    public WebElement set(){
        element = driver.findElement(By.id("btn_admin"));
        return element;
    }
    //其他
    public WebElement other(){
        element = driver.findElement(By.id("rv_right_btn"));
        return element;
    }
    //退出登录
    public WebElement logOut(){
        element = driver.findElement(By.id("btn_exit_login"));
        return element;
    }
}
