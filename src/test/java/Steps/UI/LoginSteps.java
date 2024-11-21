package Steps.UI;


import Helpers.Helper;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import Pages.LoginPage;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class LoginSteps {
     Helper helper =new Helper();
    LoginPage loginPage;
    String UserName;
    String Password;
    String Otp;
    String OTP1;
    String OTP2;
    String OTP3;
    String OTP4;
    String Browser;
    String Env;

    @Given("intializedriver")
    public void SetupDriver(List<Map<String,String>>datatable) throws Exception {
        try{

            for(Map<String,String>BrowserList:datatable){
                Browser=BrowserList.get("Browser");
                Helper.getDriver(Browser);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @And("Set Environment")
    public void PrepareEnv(List<Map<String, String>> datatable) throws Exception {
        try{
        for (Map<String, String> EnvList : datatable) {
             Env = EnvList.get("EnvironmrntType");
            helper.SetEnvBaseUrl(Env);
            }
    }
        catch(Exception e){
        throw new Exception("Failure during PrepareEnv");
    }
    }

    @Then("Login to RAP portal")
    public void LoginToOcp(List<Map<String, String>> datatable) throws Exception {
        try {
            Assert.fail("Test Allure");
            JSONObject jsonObj = new JSONObject();
            for (Map<String, String> Logincredentils : datatable) {
                UserName = Logincredentils.get("username");
                Password = Logincredentils.get("password");
                Otp = Logincredentils.get("Otp");
                OTP1= String.valueOf(Otp.charAt(0));
                OTP2 = String.valueOf(Otp.charAt(1));
                OTP3 = String.valueOf(Otp.charAt(2));
                OTP4 = String.valueOf(Otp.charAt(3));
            }
            loginPage= new LoginPage(Helper.getDriver());
            loginPage.GoToOcpPortal(helper.getUrl());
            loginPage.EnterUserCredentials(UserName, Password);
            loginPage.ClickOnLoginBtn();
            loginPage.EnterOtp(OTP1, OTP2, OTP3, OTP4);
            loginPage.ClickOnVerifyBtn();
            loginPage.AssertLoginSuccessfully();

        } catch (Exception e) {
            throw e;

        }
    }
    //@After
    public void afterScenario(Scenario scenario) {
        try {
            String screenshotName = scenario.getName();
            if (scenario.isFailed()) {
                byte[] Screenshot = ((TakesScreenshot) Helper.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(screenshotName, new ByteArrayInputStream(Screenshot));
            }
          //AllureOpen();
            Helper.getDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to serve Allure report", e);
        }
    }

    public void AllureOpen(){
            String allurePath="C:\\Program Files\\Java\\allure-2.20.1\\bin\\allure";
            String resultsDirectory = System.getProperty("user.dir")+"/target/allure-results";
            // Build the command
            ProcessBuilder processBuilder = new ProcessBuilder(allurePath, "serve", resultsDirectory);

            // Set the redirection for error and output streams
            processBuilder.redirectErrorStream(true);

            try {
                // Start the process
                Process process = processBuilder.start();

                // Read the output
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                // Wait for the process to complete
                int exitCode = process.waitFor();
                System.out.println("Allure serve exited with code: " + exitCode);

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }



