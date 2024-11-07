package Helpers;

import io.cucumber.java.Scenario;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import java.io.FileReader;
import java.util.List;

public class Helper {
    ConfigReader reader=new ConfigReader();

    @Getter
    private static WebDriver driver;
    @Getter
    String Url;
    static String values;

    public static WebDriver getDriver(String BrowserName) throws Exception {
        try {
            if (driver == null) {
                switch (BrowserName) {
                    case "Chrome":
                        driver = new ChromeDriver();
                        break;
                    case "Firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        driver = new FirefoxDriver(firefoxOptions);
                        break;
                }
            }
            driver.manage().window().maximize();
        } catch (Exception e) {
            throw new Exception(e + "Failure during intialize driver");
        }
        return driver;
    }

    public void SetEnvBaseUrl(String EnvName) {
        switch (EnvName) {
            case "Testing":
                Url = reader.getProperty("RAP_testingUrl_Base");
                break;

            case "Staging":
                Url = reader.getProperty("RAP_StagUrl_Base");
                break;
        }
        setUrl(Url);
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public void OpenuRL(String Url) {
        try {
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.get(Url);
        } catch (Exception e) {
            throw e;

        }
    }

    public static String readdata(String key_name) {

        try {
            JSONParser parser = new JSONParser();
            String currentpath = System.getProperty("user.dir");
            String filepath = currentpath + "/src/test/testdata/StcTvData.Json";
            Object obj = parser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) obj;
            values = (String) jsonObject.get(key_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return values;

    }

    public void takeScreenShot(Scenario scenario) {
        try {
            String screenshotName = scenario.getName().replaceAll("", "_");
            if (scenario.isFailed()) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "img/png", screenshotName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //findElement
    public WebElement findElement(By locator) {
        {
            try {
            } catch (Exception e) {
                throw e;
            }
        }
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        {
            try {
            } catch (Exception e) {
                throw e;
            }
            return driver.findElements(locator);
        }
    }

    //ClickOn
    public void ClickOn(By locator, boolean usingJavascript) {
        try {
            if (usingJavascript) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", findElement(locator));
            } else {
                findElement(locator).click();
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void SendKeys(By locator, String value) {
        try {
            findElement(locator).sendKeys(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public void scrollIntoView(By locator) {
        try {
            WebElement elem = findElement(locator);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth' });", elem);
        } catch (Exception e) {
            throw e;
        }
    }

    public void HoverOnelement(By locator) {

        // Create the Actions object
        Actions actions = new Actions(driver);
        // Perform the hover action
        actions.moveToElement(findElement(locator)).perform();
    }
}

