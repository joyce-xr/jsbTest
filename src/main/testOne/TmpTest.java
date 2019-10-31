import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TmpTest {

    AndroidDriver driver;

    @Test
    public void tmp(){

        //上划——oppo r9tm——x:500  y:1450~~~~~ x:500  y:250
        PointOption pointOption = new PointOption();
        new TouchAction(driver).longPress(PointOption.point(550,1500))
                .moveTo(PointOption.point(550,250))
                .release().perform();

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"网点回款管理\")").click();//进入网点回款管理模块
        driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"今日新增\")").click();//进入今日新增列表
    }

    @BeforeClass
    public  void tearUp() throws InterruptedException, MalformedURLException {

        //---------------------------------------------基础设置---------------------------------------------------

        System.out.println("------------------beforeClass----------------");
        DesiredCapabilities dsc = new DesiredCapabilities(); // 负责启动服务端时的参数设置
        //dsc.setCapability("app","D:\\1-交收嘉\\v1.9\\1.9_交收嘉_物流_测试_10.apk"); //待测apk路径
        dsc.setCapability("platformName", "Android"); //平台名称
        dsc.setCapability("platformVersion", "5.1.1"); //手机操作系统版本
        dsc.setCapability("udid", "KFEISS7D99999999");//oppo r9tm
        //dsc.setCapability("udid", "emulator-5554");//模拟器
        dsc.setCapability("deviceName", "KFEISS7D99999999");//使用的手机类型或模拟器类型  UDID

        dsc.setCapability("appPackage", "com.jiaparts.jsj_wl");//App安装后的包名,注意与原来的CalcTest.apk不一样

        dsc.setCapability("appActivity", "com.jiaparts.business.ui.MainActivity");//主Activity
        //dsc.setCapability("appActivity", "com.jiaparts.business.ui.LoginActivity");//登录activity

        //dsc.setCapability("unicodeKeyboard",true);//支持中文输入
        dsc.setCapability("resetKeyboard",true);//支持中文输入
        //dsc.setCapability("newCommandTimeout", "10");//没有新命令时的超时时间设置
        dsc.setCapability("nosign", "True");//跳过检查和对应用进行 debug 签名的步骤

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), dsc);//虚拟机默认地址

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//全局设置显示等待


        //切换账号登录，只第一次启动后登录执行
        driver.findElementById("btn_switch_login").click();


        login("12711111115","111111");//起始网点登录

    }

    //登录
    public void login(String userName , String passWord) throws InterruptedException {
        //--------------------------------------登录--------------------------------

        System.out.println("-----------------------登录方法--------------------------");

        //输入账号密码，登录
        driver.findElementById("edt_name").sendKeys(userName);
        driver.findElementById("edt_pwd").sendKeys(passWord);
        driver.findElementById("btn_login").click();

        //Thread.sleep(5000);
    }

    //退出
    public void logout() throws InterruptedException {

        System.out.println("-----------------------退出登录方法--------------------------");

        //Thread.sleep(5000);

        //退出
        driver.findElementById("btn_admin").click();//点击设置
        driver.findElementById("rv_right_btn").click();//点击“其他”
        driver.findElementById("btn_exit_login").click();//点击退出登录

        //Thread.sleep(5000);
    }

    @AfterClass
    public  void tearDown() throws InterruptedException {

        System.out.println("----------------AfterClass-----------------");

        driver.quit();
    }

}
