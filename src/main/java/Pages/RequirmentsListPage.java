package Pages;

import Helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequirmentsListPage {

    Helper help=new Helper ();
    private WebDriver driver;

    By RequirmentMnagement_MenuBtn = By.xpath("//a[@href='/requirements-management']");
    By RequirmentMnagementList_MenuBtn = By.xpath("//a[@href='/requirements-management/requirements']");
    By AddRequirmentBttn=By.xpath("//moj-icon[@name='add']");

    public RequirmentsListPage(WebDriver driver) {
        this.driver = driver;
    }
    public void GoToListing() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(RequirmentMnagement_MenuBtn));
            help.HoverOnelement(RequirmentMnagement_MenuBtn);
            //help.ClickOn(RequirmentMnagement_MenuBtn,true);
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(RequirmentMnagementList_MenuBtn));
            help.ClickOn(RequirmentMnagementList_MenuBtn,true);
        } catch (Exception e) {
            throw e;
        }
    }
    public void OpenAddNewRequirmentPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(AddRequirmentBttn));
            help.ClickOn(AddRequirmentBttn,false);
        } catch (Exception e) {
            throw e;
        }
    }
}
