package Steps.APIs;

import Helpers.ConfigReader;
import Helpers.Helper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CRInquirySteps {
    String CRnumber;
    Response response;
    String Url;
    String UserName;
    String Passsword;
    String Env;
    Helper helper = new Helper();
    ConfigReader reader = new ConfigReader();
    String Token;

    @Given("Set the Environment")
    public void PrepareEnv(List<Map<String, String>> datatable) throws Exception {
        try {

            for (Map<String, String> EnvList : datatable) {
                Env = EnvList.get("EnvironmrntType");
                switch (Env) {
                    case "Testing":
                        Url = reader.getProperty("TakamolBaseUrlforTest");
                        UserName = reader.getProperty("Takamol_User_Test");
                        Passsword = reader.getProperty("Takamol_Passw0rd_Test");
                        break;
                    case "Production":
                        Url = reader.getProperty("TakamolBaseUrlforLive");
                        UserName = reader.getProperty("Takamol_User_Live");
                        Passsword = reader.getProperty("Takamol_Passw0rd_Live");
                        break;

                }
            }
        } catch (Exception e) {
            throw new Exception("Failure during PrepareEnv");
        }
    }

    @And("Login to Takamol API")
    public Response LoginTakamolApi() throws Exception {
        try {
            response = given()
                    .relaxedHTTPSValidation()
                    .auth().preemptive().basic(UserName, Passsword)
                    .log().all()
                    .post(Url + reader.getProperty("Takamol_Path"))
                    .then().log().all().extract().response();
            return response;
        } catch (Exception e) {
            throw new Exception("Failure during PrepareEnv");
        }
    }

    @And("validate that status code is 200 Ok")
    public void validateLoginApi() throws Exception {
        try {
            response.getStatusCode();
            Assert.assertTrue(response.getStatusCode() == 200);
        } catch (Exception e) {
            throw new Exception("Login API status code isn't 200");
        }
    }

    @And("Extract access token from Login API")
    public void Extract_Token() throws Exception {
        try {
            Token = response.jsonPath().get("access_token").toString();
        } catch (Exception e) {
            throw new Exception("Failure during extract token ");
        }
    }

    @Then("Get CR information By CR Number")
    public void GetCRinformation(List<Map<String, String>> datatable) throws Exception {
        try {
            for (Map<String, String> EnvList : datatable) {
                CRnumber = EnvList.get("CR-Number");
                response = given()
                        .relaxedHTTPSValidation()
                        .queryParam("CRNumber", CRnumber)
                        .headers("Authorization", "Bearer " + Token)
                        .log().all()
                        .get(Url + reader.getProperty("CRinquiryPath"))
                        .then().log().all().extract().response();
            }
            Assert.assertTrue(response.getStatusCode() == 200, "GetCRinformation API didn't pass");
        } catch (Exception e) {
            throw new Exception("Failure during extract token ");
        }
    }

    @And("validate that response return all CR information")
    public void validateCrInquiryResponse() throws Exception {
        try {
            Assert.assertEquals(response.jsonPath().get("data.CRNumber"), CRnumber);
        } catch (Exception e) {
            throw new Exception("Failure during validate CrInquiry Response ");
        }
    }

    @And("validate that response show status {string}")
    public void validateThatCrInquiryStatus(String CRStatus) throws Exception {
        try {
            Assert.assertTrue(response.jsonPath().get("data.CRStatus").toString().equalsIgnoreCase(CRStatus));
        } catch (Exception e) {
            throw new Exception("Failure during validate CrInquiry Response ");
        }
    }
}
