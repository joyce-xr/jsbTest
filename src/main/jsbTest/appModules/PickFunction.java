import io.appium.java_client.android.AndroidDriver;

public class PickFunction {

    public static void pick(AndroidDriver driver, String mainSn){
        System.out.println("--------------付货PickFunction--------------");
        System.out.println("----------------付货运单号：----------------" +mainSn);

        HomePage homePage = new HomePage(driver);
        PickPage pickPage = new PickPage(driver);

        homePage.getPickIcon().click();

        //搜索运单
        pickPage.getBillNoSearch().sendKeys(mainSn);
        pickPage.getSearchBtn().click();

        //进入运单详情
        pickPage.getBillInfo().click();

        //付货
        pickPage.getPickBtn().click();
        pickPage.getEnsureBtn().click();

        //返回
        pickPage.getReturnBtn().click();

    }
}
