import io.appium.java_client.android.AndroidDriver;

public class LoginFunction {
    //不切换登录方式，直接账号密码登录——适用于切换账号等非首次登录状态
    public static void login(AndroidDriver driver, String userName, String passWord){

        System.out.println("-----------------------登录方法LoginFunction--------------------------");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.getUserName().sendKeys(userName);
        loginPage.getPassWord().sendKeys(passWord);
        loginPage.getLoginBtn().click();

    }

    //首次登录方法
    public static void firstLogin(AndroidDriver driver, String userName, String passWord){

        System.out.println("-----------------------登录方法LoginFunction--------------------------");

        LoginPage loginPage = new LoginPage(driver);

        //切换到账号密码登录
        loginPage.getSwitchBtn().click();

        loginPage.getUserName().sendKeys(userName);
        loginPage.getPassWord().sendKeys(passWord);
        loginPage.getLoginBtn().click();

    }
}
