import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import sun.plugin2.main.client.WDonatePrivilege;

public class NewBillPage {
    AndroidDriver driver;
    WebElement element;

    public NewBillPage(AndroidDriver driver){
        this.driver = driver;
    }

    //发货人电话
    public WebElement getSendPhone(){
        element = driver.findElement(By.id("edt_deliver_phone"));
        return element;
    }

    //收货人电话
    public WebElement getRcvPhone(){
        element = driver.findElement(By.id("edt_deliver_phone"));
        return element;
    }

    //到达站
    public WebElement getArrival(){
        element = driver.findElement(By.id("tv_arrive"));
        return element;
    }

    //包裹数
    public WebElement getPackNum(){
        element = driver.findElement(By.id("etAmount"));
        return element;
    }

    //代收金额
    public WebElement getGoodsAmt(){
        element = driver.findElement(By.id("edt_goods_price"));
        return element;
    }

    //运费方式
    public WebElement getShipPmt(){
        element = driver.findElement(By.id("tv_pay_way"));
        return element;
    }

    //运费金额
    public WebElement getShipAmt(){
        element = driver.findElement(By.id("edt_money"));
        return element;
    }

    //其他费用
    public WebElement getOtherAmt(){
        element = driver.findElement(By.id("edt_other_money"));
        return element;
    }

    //物流备注
    public WebElement getRemark(){
        element = driver.findElement(By.id("edt_remark"));
        return element;
    }

    //收件按钮
    public WebElement getReceiveButton(){
        element = driver.findElement(By.id("btn_receiver"));
        return element;
    }

    //暂存按钮
    public WebElement getSaveButton(){
        element = driver.findElement(By.id("btn_save"));
        return element;
    }
}
