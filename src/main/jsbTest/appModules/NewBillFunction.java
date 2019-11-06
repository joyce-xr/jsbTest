import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class NewBillFunction {

    public static void newBill(AndroidDriver driver, String billNotes, String packNum, String goodsAmt, String shipPmt, String shipAmt, String otherAmt, String arrival){

        System.out.println("----------------------进入受理开单TestModules----------------------");
        System.out.println("----------------------打印参数：----------------------" +billNotes);

        //页面对象
        HomePage homePage = new HomePage(driver);
        NewBillPage newBillPage = new NewBillPage(driver);

        //点击受理开单
        homePage.getNewBillIcon().click();

        //收发货人电话
        newBillPage.getSendPhone().click();
        newBillPage.getSendPhone().sendKeys("12700000000");
        driver.getKeyboard().pressKey("KEYCODE_1");
        newBillPage.getRcvPhone().click();
        newBillPage.getRcvPhone().sendKeys("1270000000");
        driver.getKeyboard().pressKey("KEYCODE_1");

        driver.hideKeyboard();

        //到达站
        newBillPage.getArrival().click();
        newBillPage.getArrivalNet1(arrival).click();

        //上划——oppo r9tm——x:500  y:1450~~~~~ x:500  y:250
        PointOption pointOption = new PointOption();
        new TouchAction(driver).longPress(PointOption.point(500,1450))
                .moveTo(PointOption.point(500,250))
                .release().perform();

        //包裹数
        newBillPage.getPackNum().click();
        newBillPage.getPackNum().clear();
        newBillPage.getPackNum().sendKeys(packNum);
        newBillPage.getPackNumEnsure().click();

        //代收金额
        newBillPage.getGoodsAmt().sendKeys(goodsAmt);

        //如果代收金额大于0，则新增收款账户
        if(Integer.parseInt(goodsAmt)>0){
            System.out.println("-------------------添加收款账户-----------------------");
            newBillPage.getAccount().click();
            newBillPage.getNewAccount().click();
            newBillPage.getAccountNo().sendKeys("6222000000000001");
            newBillPage.getAccountName().sendKeys("appium新户名");
            newBillPage.getAccountBank().sendKeys("appium新银行");
            newBillPage.getAccountSaveBtn().click();
        }

        //运费
        newBillPage.getShipPmt().click();//运费方式
        newBillPage.getShipPmtVal(shipPmt).click();
        newBillPage.getShipAmt().sendKeys(shipAmt);//运费金额

        //其他费用
        newBillPage.getOtherAmt().sendKeys(otherAmt);

        //物流备注
        newBillPage.getRemarks().sendKeys(billNotes);

        //点击收件
        newBillPage.getReceiveButton().click();

    }
}
