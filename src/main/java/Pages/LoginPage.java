package Pages;

import Helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage  {
    Helper help=new Helper ();
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    By UserNameField = By.xpath("//input[contains(@id,'mat-input')and @type='text']");
    By PasswordField = By.xpath("//input[contains(@id,'mat-input')and @type='password']");
    By LogibBtn = By.cssSelector("button[type='submit']");

    //Otp
    By Otp1 = By.cssSelector("input[id*='otp_0']");
    By Otp2 = By.cssSelector("input[id*='otp_1']");
    By Otp3 = By.cssSelector("input[id*='otp_2']");
    By Otp4 = By.cssSelector("input[id*='otp_3']");
    By BackBtn = By.xpath("//a[contains(@href,'login')]");
    By VrifyBtn = By.cssSelector("button[type*='button']");
    By HomeIcon = By.xpath("//moj-icon[contains(@name,'home')]");

    public void GoToOcpPortal(String OCPUrl) {
        try {
            help.OpenuRL(OCPUrl);
        } catch (Exception e) {
            throw e;
        }
    }

    public void EnterUserCredentials(String UserName, String Password) {
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(UserNameField));
            help.SendKeys(UserNameField, UserName);
            help.SendKeys(PasswordField, Password);
        } catch (Exception e) {
            throw e;

        }
    }

    public void ClickOnLoginBtn() {
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(LogibBtn));
            help.ClickOn(LogibBtn, true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void EnterOtp(String otp1, String otp2, String otp3, String otp4) {
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(BackBtn));
            help.SendKeys(Otp1, otp1);
            help.SendKeys(Otp2, otp2);
            help.SendKeys(Otp3, otp3);
            help.SendKeys(Otp4, otp4);
        } catch (Exception e) {
            throw e;
        }
    }

    public void ClickOnVerifyBtn() {
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(VrifyBtn));
            help.ClickOn(VrifyBtn, true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void AssertLoginSuccessfully() {
        try {
            new WebDriverWait(driver,
                    Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(HomeIcon));
            Assert.assertTrue(help.findElement(HomeIcon).isDisplayed());
        } catch (Exception e) {
            throw e;
        }
    }
}
