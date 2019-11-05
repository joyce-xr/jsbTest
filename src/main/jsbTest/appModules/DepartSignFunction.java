import io.appium.java_client.android.AndroidDriver;

public class DepartSignFunction {
    public static void departSign(AndroidDriver driver){
        HomePage homePage = new HomePage(driver);
        DepartArrivalPage departArrivalPage = new DepartArrivalPage(driver);

        //进入到车签收页面
        homePage.getDepartArrivalIcon().click();

        departArrivalPage.getSignBtn().click();//点击到车签收
        departArrivalPage.getEnsureBtn().click();//点击确认签收
        departArrivalPage.getReturnBtn().click();//返回首页

    }
}
