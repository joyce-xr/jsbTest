import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasicTest {
    AndroidDriver driver;

    //受理开单——测试数据
    @DataProvider(name = "billData")
    public static Object[][] getBillData() throws IOException {

        System.out.println("-------------------进入DataProvider-------------------");

        Object[][] results;

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("D:\\IdeaProjects\\appiumTest\\billData-tmp.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);

        //得到最后一行的行标，（比实际行数小1）
        int rowNum = sheet.getLastRowNum(); //15

        //得到有效列数，（比列标大1）
        HSSFRow row = sheet.getRow(1);
        int colNum = row.getLastCellNum(); //7

        System.out.println("------------表格行列数------------" + rowNum + ", " + colNum);

        results = new Object[rowNum][colNum-1];

        for(int i = 1; i<rowNum+1 ; i++){
            System.out.println("--------excel数据读取——一层循环-------" + i);
            row = sheet.getRow(i);
            for(int j = 1; j<colNum ; j++){
                System.out.println("--------excel数据读取——二层循环-------" + j);
                results[i-1][j-1] = row.getCell(j).getStringCellValue();
                System.out.println("-------returls[i][j]--------" + results[i-1][j-1]);
            }
        }

        return results;
    }

    //受理开单——测试脚本
    @Test(dataProvider = "billData")
    public void testNewBill(String billNotes, String packNum, String goodsAmt, String shipPmt, String shipAmt, String otherAmt) throws InterruptedException {
        System.out.println("----------------------进入Test----受理开单----------------------");

        System.out.println("---------dataProvider(billData)传参------------" + billNotes + "包裹数传参：" +packNum);

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
        //Thread.sleep(3000);
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
                .moveTo(PointOption.point(500,250))
                .release().perform();

        //包裹数
        driver.findElementById("etAmount").click();
        //Thread.sleep(2000);
        driver.findElementById("edt_count").clear();
        driver.findElementById("edt_count").sendKeys(packNum);
        driver.findElementById("btn_ensure").click();
        //代收金额——0
        driver.findElementById("edt_goods_price").sendKeys(goodsAmt);

        //如果代收金额大于0，则新增收款账户
        if(Integer.parseInt(goodsAmt)>0){
            System.out.println("-------------------添加收款账户-----------------------");
            driver.findElementById("tv_receive_account_edit").click();
            //Thread.sleep(3000);
            driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"收款账号\")").click();
            driver.findElementById("edt_account").sendKeys("6222000000000001");
            driver.findElementById("edt_account_name").sendKeys("appium新户名");
            driver.findElementById("edt_account_no").sendKeys("appium新银行");
            driver.findElementById("btn_save").click();
            //Thread.sleep(3000);
        }

        //运费方式
        driver.findElementById("tv_pay_way").click();
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+shipPmt+"\")").click();    //选择现付
        //运费金额
        driver.findElementById("edt_money").sendKeys(shipAmt);
        //其他费用
        driver.findElementById("edt_other_money").sendKeys(otherAmt);
        //物流备注
        driver.findElementById("edt_remark").sendKeys(billNotes);

        //收件按钮
        driver.findElementById("btn_receiver").click();

        //获取运单号
        String showSn = driver.findElementById("tv_num").getText().split("：")[1];

        //Thread.sleep(3000);

        //暂存按钮
        //driver.findElementById("btn_save");

        //收件后返回首页
        driver.findElementById("img_left_btn").click();

        //断言：如果运费是现付的，今日新增中有记录
        if(shipPmt.equals("现付")){

            System.out.println("------------进入现付运单的验证-------------");

            //进入网点回款管理
            //上划——oppo r9tm——x:500  y:1450~~~~~ x:500  y:250
            new TouchAction(driver).longPress(PointOption.point(550,1500))
                    .moveTo(PointOption.point(550,250))
                    .release().perform();

            driver.findElementByAndroidUIAutomator("new UiSelector().text(\"网点回款管理\")").click();//进入网点回款管理模块
            driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"今日新增\")").click();//进入今日新增列表

            //验证最新一条数据是现付运费
            String showSn_b = driver.findElement(By.id("tv_no")).getText();//可以找到多个，只取第一个
            String shipPmt_b = driver.findElement(By.id("tv_type")).getText();
            String shipAmt_b = driver.findElement(By.id("tv_amount")).getText().split("\\s+")[1];//直接获取的格式是¥ 22.00，按空格分隔取后段

            Double shipAmt_d = Double.parseDouble(shipAmt);
            Double shipAmt_b_d = Double.parseDouble(shipAmt_b);

            Assert.assertEquals(showSn_b,showSn);
            Assert.assertEquals(shipPmt_b,shipPmt+"运费");
            Assert.assertEquals(shipAmt_d,shipAmt_b_d);

            //返回首页
            driver.findElement(By.id("img_left_btn")).click();
            ((WebElement)driver.findElements(By.className("android.widget.ImageView")).get(0)).click();

        }

    }

    @Test
    public void depart() throws InterruptedException {
        System.out.println("----------------------进入Test--发车------------------------");

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"配载发车\")").click();
        //Thread.sleep(3000);
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"大连\")").click();//展开大区
        //Thread.sleep(3000);
        driver.findElementByXPath("//*[@resource-id='com.jiaparts.jsj_wl:id/tv_title_child'][@text='大连']").click();
        //Thread.sleep(3000);
        driver.findElementById("btn_send_all").click();//全部发车
        driver.findElementById("btn_ensure").click();//确认发车
        //Thread.sleep(3000);
        driver.findElementById("img_left_btn").click();//返回

        //发车后切换账号
        logout();
        login("12711111135","111111");//大连员工

    }

    @DataProvider(name = "packSn")
    public Object[][] getPackSn() {
        System.out.println("------------------DataProvider(name = packSn)----------------");
        try {

            //数据库查询刚发车的包裹码
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://172.23.6.92:3306/jsb", "testuser", "test&20190911");
            Statement statement = conn.createStatement();

            //查询包裹码
            String packSn = "select PackSN from packsn_used where BillSN in " +
                    "(select BillSN from waybill_logistics where DepartID=" +
                    "(select id from depart where NetID=" +
                    "(select NetID from jsb.logistics_net_employee where UserID=(select id from mml.`user` where BindPhone=12711111115)) ORDER BY id DESC limit 1))";

            ResultSet rs = statement.executeQuery(packSn);

            //不知道有多少个包裹，定义一个List
            List<Object[]> records = new ArrayList<Object[]>();
            while (rs.next()) {
                String record[] = new String[1];
                record[0] = rs.getString(1);//注意索引值从1开始
                System.out.println("-----------DataProvider中打印包裹码(record[0])-----------" + record[0]);
                records.add(record);
            }

            //将list转化为二位数组
            Object[][] results = new Object[records.size()][];
            for (int i = 0; i < records.size(); i++) {
                results[i] = records.get(i);//get方法索引值从0开始
            }

            //打印二维数组
            for(int i=0; i<results.length; i++){
                for(int j=0; j<results[i].length; j++){
                    System.out.println("-------------打印二维数组results[][]-----------" + results[i][j]);
                }
            }

            return results;
        }catch (Exception e){
            System.out.print("----------MYSQL ERROR:" + e.getMessage());
            return null;
        }
    }

    @Test(dataProvider = "packSn")
    public void signDepart(String packSn) throws ClassNotFoundException, SQLException, InterruptedException {

        System.out.println("------------------扫码签车方法----------------");
        System.out.println("-----------------------包裹码-----------------------" + packSn);

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"扫码签车\")").click();
        //Thread.sleep(3000);
        driver.findElementById("edt_scan").sendKeys(packSn);
        //driver.getKeyboard().pressKey("KEYCODE_ENTER");//模拟回车按键
        //小键盘回车键
        driver.getKeyboard().pressKey("KEYCODE_NUMPAD_ENTER");

    }

    //到车管理
    @Test
    public void departManage() throws InterruptedException {

        System.out.println("------------------到车管理方法----------------");

        //先完成确认签车
        driver.findElementById("tv_confirm").click();
        //Thread.sleep(2000);
        driver.findElementById("btn_ensure").click();
        //Thread.sleep(5000);

        //到车管理
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"到车管理\")").click();
        //Thread.sleep(3000);
        WebElement departInfo = (WebElement) driver.findElementsById("tv_sign").get(0); //查看详情，索引从0开始
        departInfo.click();

        //Thread.sleep(3000);
        driver.findElementById("btn_confirm_sign").click();//确认签收
        //Thread.sleep(5000);
    }

    //到车签收
    @Test
    public void departArrival() throws InterruptedException {

        System.out.println("------------------到车签收方法----------------");

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"到车签收\")").click();
        //Thread.sleep(3000);
        driver.findElementById("tv_sign").click();//点击到车签收
        //Thread.sleep(3000);
        driver.findElementById("btn_ensure").click();//点击确认
        //Thread.sleep(2000);
        driver.findElementById("rv_left_btn").click();//点击返回
    }

    @DataProvider(name = "mainSn")
    public Object[][] pickGoods(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://172.23.6.92:3306/jsb","testuser","test&20190911");
            Statement statement = conn.createStatement();

            //查询已到车签收的运单号mainsn
            String sql = "select ShowSN from waybill_logistics where DepartID=\n" +
                    "(select id from depart where ArriveNetID=\n" +
                    "(select NetID from jsb.logistics_net_employee where UserID=(select id from mml.`user` where BindPhone=12711111135)) and IsSign=1 ORDER BY id DESC limit 1)";

            ResultSet rs = statement.executeQuery(sql);

            //不知道有多少个包裹，定义一个List
            List<Object[]> records = new ArrayList<Object[]>();
            while (rs.next()) {
                String record[] = new String[1];
                record[0] = rs.getString(1);//注意索引值从1开始
                System.out.println("-----------DataProvider中打印运单号(record[0])-----------" + record[0]);
                records.add(record);
            }

            //将list转化为二位数组
            Object[][] results = new Object[records.size()][];
            for (int i = 0; i < records.size(); i++) {
                results[i] = records.get(i);//get方法索引值从0开始
            }

            //打印二维数组
            for(int i=0; i<results.length; i++){
                for(int j=0; j<results[i].length; j++){
                    System.out.println("-------------mainsn：打印二维数组results[][]-----------" + results[i][j]);
                }
            }

            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //付货
    @Test(dataProvider = "mainSn")
    public void pick(String mainSn) throws InterruptedException {
        System.out.println("--------------付货--------------");

        System.out.println("----------------付货运单号：----------------" +mainSn);

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"付货\")").click();
        //Thread.sleep(3000);
        driver.findElementById("edt_search").sendKeys(mainSn);//输入单号
        driver.findElementById("btn_search").click();//点击搜索
        //Thread.sleep(2000);

        driver.findElementById("iv_right").click();//进入运单详情
        //Thread.sleep(2000);

        driver.findElementById("btn_pay_goods").click();//点击付货
        //Thread.sleep(1000);
        driver.findElementById("tv_sure").click();//确认付货

        //Thread.sleep(3000);


        String status = driver.findElementById("tv_status").getText();//状态
        String shipPmt = driver.findElementById("tv_yf_pmt").getText();//运费方式
        String yf = driver.findElementById("tv_yf").getText();//运费金额  格式：¥ 20.00
        Double shipAmt =Double.parseDouble(yf.split("\\s+")[1]);

        try{
            String loan = driver.findElementById("tv_loan").getText();//代收款
            String service_charge = driver.findElementById("tv_service_charge").getText();//代收手续费

            System.out.println("-----------打印代收款-------------" + loan + ", " + service_charge);
        }catch (NoSuchElementException e){
            System.out.println("---------异常信息---------" + mainSn + "没有代收款");
        }

        Assert.assertEquals(status,"已付货");//校验运单状态是否为“已付货”

        driver.findElementById("img_left_btn").click();//返回首页


        //进入网点回款管理
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
