import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PrimaryTest {
    AndroidDriver driver;

    /**
     * 收件~配载发车~到车签收
     * @param billNotes
     * @param packNum
     * @param goodsAmt
     * @param shipPmt
     * @param shipAmt
     * @param otherAmt
     * @param arrival
     */
    @Test(dataProvider = "testData")
    public void primary(String billNotes, String packNum, String goodsAmt, String shipPmt, String shipAmt, String otherAmt, String arrival){
        System.out.println("-------------进入primary---------");

        NewBillFunction.newBill(driver, billNotes, packNum, goodsAmt, shipPmt, shipAmt, otherAmt, arrival);
        DepartFunction.depart(driver,arrival);
        LoginFunction.logOut(driver);
        LoginFunction.login(driver,"12711111135","111111");
        DepartSignFunction.departSign(driver);

    }


    @Test(dataProvider = "pickShowSn")
    public void primaryPick(String showSn){
        PickFunction.pick(driver, showSn);
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws IOException {
        return ExcelUtil.getTestData("billData.xls","basicData");
    }

    @DataProvider(name = "pickShowSn")
    public Object[][] pickShowSn(){

        String sql = "select ShowSN from waybill_logistics where DepartID=\n" +
                "(select id from depart where ArriveNetID=\n" +
                "(select NetID from jsb.logistics_net_employee where UserID=(select id from mml.`user` where BindPhone=12711111135)) and IsSign=1 ORDER BY id DESC limit 1)";

        return MysqlUtil.getSigalDataFromMySql(sql);
    }

    @BeforeClass
    public void setUp() throws MalformedURLException {
        System.out.println("------------------beforeClass----------------");
        DesiredCapabilities dsc = new DesiredCapabilities(); // 负责启动服务端时的参数设置
        //dsc.setCapability("app","D:\\1-交收嘉\\v1.9\\1.9_交收嘉_物流_测试_10.apk"); //待测apk路径，进行安装测试时需设置此参数
        dsc.setCapability("platformName", "Android"); //平台名称
        dsc.setCapability("platformVersion", "5.1.1"); //手机操作系统版本
        dsc.setCapability("udid", "KFEISS7D99999999");//oppo r9tm
        //dsc.setCapability("udid", "emulator-5554");//模拟器
        dsc.setCapability("deviceName", "KFEISS7D99999999");//使用的手机类型或模拟器类型  UDID
        dsc.setCapability("appPackage", "com.jiaparts.jsj_wl");//App安装后的包名,注意与原来的CalcTest.apk不一
        dsc.setCapability("appActivity", "com.jiaparts.business.ui.MainActivity");//主Activity
        //dsc.setCapability("appActivity", "com.jiaparts.business.ui.LoginActivity");//登录activity
        //dsc.setCapability("unicodeKeyboard",true);//支持中文输入
        dsc.setCapability("resetKeyboard",true);//支持中文输入
        //dsc.setCapability("newCommandTimeout", "10");//没有新命令时的超时时间设置
        dsc.setCapability("nosign", "True");//跳过检查和对应用进行 debug 签名的步骤

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), dsc);//虚拟机默认地址
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//全局设置显示等待

        //首次登录
        LoginFunction.firstLogin(driver,"12711111115","111111");//老板，沈阳和平
    }

    @AfterClass
    public void tearDown(){
        System.out.println("----------------AfterClass-----------------");

        driver.quit();
    }
}
