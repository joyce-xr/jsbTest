import io.appium.java_client.android.AndroidDriver;

public class DepartFunction {

    //单个大区发车，不包含下属子网点的
    public static void depart(AndroidDriver driver, String netName){

        HomePage homePage = new HomePage(driver);
        DepartPage departPage = new DepartPage(driver);

        //点击配载发车
        homePage.getDepartIcon().click();

        //展开大区分组
        departPage.getLevelOne(netName).click();

        //点击大区的二级分组
        departPage.getLevelTwo(netName).click();

        //发车
        departPage.getSendAll().click();
        departPage.getEnsure().click();
        departPage.getReturnBtn().click();


    }
}
