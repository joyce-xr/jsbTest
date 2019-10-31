import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class HeadFirst {

    public static void main(String args[] ) throws Exception {
        AndroidDriver driver;
        System.out.println("------------------1----------------");
        DesiredCapabilities dsc = new DesiredCapabilities(); // 负责启动服务端时的参数设置
        //dsc.setCapability("app","D:\\1-交收嘉\\v1.9\\1.9_交收嘉_物流_测试_10.apk"); //待测apk路径
        dsc.setCapability("platformName", "Android"); //平台名称
        dsc.setCapability("platformVersion", "5.1.1"); //手机操作系统版本
        dsc.setCapability("udid", "KFEISS7D99999999");//oppo r9tm
        //dsc.setCapability("udid", "emulator-5554");//模拟器
        dsc.setCapability("deviceName", "KFEISS7D99999999");//使用的手机类型或模拟器类型  UDID

        System.out.println("----------------2------------------");
        dsc.setCapability("appPackage", "com.jiaparts.jsj_wl");//App安装后的包名,注意与原来的CalcTest.apk不一样
        System.out.println("----------------3------------------");
        dsc.setCapability("appActivity", "com.jiaparts.business.ui.MainActivity");//主Activity
        //dsc.setCapability("appActivity", "com.jiaparts.business.ui.LoginActivity");//登录activity

        System.out.println("-----------------4-----------------");
        //dsc.setCapability("unicodeKeyboard",true);//支持中文输入
        dsc.setCapability("resetKeyboard",true);//支持中文输入
        //dsc.setCapability("newCommandTimeout", "10");//没有新命令时的超时时间设置
        dsc.setCapability("nosign", "True");//跳过检查和对应用进行 debug 签名的步骤

        System.out.println("----------------5------------------");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), dsc);//虚拟机默认地址

        System.out.println("----------------6------------------");

        Thread.sleep(5000);

        //切换账号登录
        driver.findElementById("btn_switch_login").click();

        //输入账号密码，登录
        driver.findElementById("edt_name").sendKeys("12711111115");
        driver.findElementById("edt_pwd").sendKeys("111111");
        driver.findElementById("btn_login").click();

        Thread.sleep(5000);

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"受理开单\")").click();

        //发货人电话
        driver.findElementById("edt_deliver_phone").click();
        driver.findElementById("edt_deliver_phone").sendKeys("12700000000");
        driver.getKeyboard().pressKey("KEYCODE_1");
        //收货人电话
        driver.findElementById("edt_receiver_phone").click();
        driver.findElementById("edt_receiver_phone").sendKeys("1270000000");
        driver.getKeyboard().pressKey("KEYCODE_2");

        driver.hideKeyboard(); //输入后隐藏键盘
        //到达站
        driver.findElementById("tv_arrive").click();
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"大连\")").click();


//        List<WebElement> arrives1 = driver.findElementsById("tv_value");//当前屏幕展示出的到达站
//        System.out.println("到达站数量：" + arrives1.size());
//        for(int i = 0 ; i<arrives1.size() ; i++){
//            System.out.println("--------------" + i);
//            if(arrives1.get(i).getText().equals("杭州大区")){
//                System.out.println("-------------进入if------------");
//                arrives1.get(i).click();
//                break;
//            }
//        }

        //上划——oppo r9tm——x:500  y:1450~~~~~ x:500  y:250
        PointOption pointOption = new PointOption();
        new TouchAction(driver).longPress(PointOption.point(500,1450))
                                .moveTo(pointOption.point(500,250))
                                .release().perform();

        //包裹数
        driver.findElementById("etAmount").clear();
        driver.findElementById("etAmount").sendKeys("1");
        //代收金额——0
        driver.findElementById("edt_goods_price").sendKeys("0");
        //运费方式
        driver.findElementById("tv_pay_way").click();
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"现付\")").click();    //选择现付
        //运费金额
        driver.findElementById("edt_money").sendKeys("100");
        //其他费用
        driver.findElementById("edt_other_money").sendKeys("-50");
        //物流备注
        driver.findElementById("edt_remark").sendKeys("appium添加");

        //收件按钮
        driver.findElementById("btn_receiver").click();

        Thread.sleep(3000);

        //暂存按钮
        //driver.findElementById("btn_save");




//
//        Thread.sleep(5000);
//
//        //退出
//        driver.findElementById("btn_admin").click();
//        driver.findElementById("rv_right_btn").click();
//        driver.findElementById("btn_exit_login").click();
//
//        Thread.sleep(5000);

        driver.quit();
    }
}
