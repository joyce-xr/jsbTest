import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DepartPage {

    AndroidDriver driver;
    WebElement element;

    public DepartPage(AndroidDriver driver){
        this.driver = driver;
    }


    //一级菜单
    public WebElement getLevelOne(String levelOne){
        element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+levelOne+"\")");
        return element;
    }

    //二级菜单
    public WebElement getLevelTwo(String levelTwo){
        element = driver.findElementByXPath("//*[@resource-id='com.jiaparts.jsj_wl:id/tv_title_child'][@text='"+levelTwo+"']");
        return element;
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
