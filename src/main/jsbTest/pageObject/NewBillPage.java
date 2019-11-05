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

    //包裹数输入弹框的确认按钮
    public WebElement getPackNumEnsure(){
        element = driver.findElement(By.id("btn_ensure"));
        return element;
    }

    //代收金额
    public WebElement getGoodsAmt(){
        element = driver.findElement(By.id("edt_goods_price"));
        return element;
    }

    //收款账户-主
    public WebElement getAccount(){
        element = driver.findElement(By.id("tv_receive_account_edit"));
        return element;
    }

    //新增收款账户-tab
    public WebElement getNewAccount(){
        element = driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"收款账号\")");
        return element;
    }

    //收款账号-输入框
    public WebElement getAccountNo(){
        element = driver.findElement(By.id("edt_account"));
        return element;
    }

    //收款账户名-输入框
    public WebElement getAccountName(){
        element = driver.findElement(By.id("edt_account_name"));
        return  element;
    }

    //收款银行-输入框
    public WebElement getAccountBank(){
        element = driver.findElement(By.id("edt_account_no"));
        return  element;
    }

    //新增收款账户-保存按钮
    public  WebElement getAccountSaveBtn(){
        element = driver.findElement(By.id("btn_save"));
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
    public WebElement getRemarks(){
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

    //返回按钮
    public WebElement getReturnBtn(){
        element = driver.findElement(By.id("img_left_btn"));
        return element;
    }

    //运单号(格式例如——-------托运单号：0000139)
    public String getShowSn(){
        String showSn = driver.findElement(By.id("tv_num")).getText().split("：")[1];
        return showSn;
    }
}
