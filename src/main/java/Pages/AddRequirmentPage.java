package Pages;

import Helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AddRequirmentPage {

    Helper help = new Helper();
    private WebDriver driver;

    public AddRequirmentPage(WebDriver driver) {
        this.driver = driver;
    }

    By AddRequirmentPageTitle = By.cssSelector(".breadcrumb__item__title");

    public void VerifyAddPageOpened(){
       try{

           Assert.assertEquals(help.findElement(AddRequirmentPageTitle).getText(), "إضافة متطلب جديد");
       }
       catch (Exception e){

throw  e;
       }


    }

}
