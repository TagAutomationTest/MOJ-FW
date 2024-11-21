package Steps.APIs;

import Helpers.ConfigReader;
import Helpers.DataBaseConnect;
import Helpers.Helper;
import Helpers.Payloads;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CRInquirySteps {
    Logger log = Logger.getLogger(CRInquirySteps.class);
    String CRnumber;
    Response response;
    String TakamolBaseUrl;
    String OcpBaseUrl;
    String TakamolUserName;
    String TakamolPasssword;
    String PortalUserName;
    String PortalPassword;
    String Env;
    DataBaseConnect obj = new DataBaseConnect();
    ConfigReader reader = new ConfigReader();
    String Token;
    String Eotp;
    String otp;
    String OCPToken;
    String ThirdpartyName;

    @Given("Set the Environment")
    public void PrepareEnv(List<Map<String, String>> datatable) throws Exception {
        try {

            for (Map<String, String> EnvList : datatable) {
                Env = EnvList.get("EnvironmrntType");
                switch (Env) {
                    case "Testing":
                        //  Takamol
                        TakamolBaseUrl = reader.getProperty("TakamolBaseUrlforTest");
                        TakamolUserName = reader.getProperty("Takamol_User_Test");
                        TakamolPasssword = reader.getProperty("Takamol_Passw0rd_Test");

                        //  Portal
                        OcpBaseUrl = reader.getProperty("OCP_Testing_BaseUrl");
                        PortalUserName = reader.getProperty("PortalUser");
                        PortalPassword = reader.getProperty("PortalPass");
                        break;

                    case "Production":
                        //  Takamol
                        TakamolBaseUrl = reader.getProperty("TakamolBaseUrlforLive");
                        TakamolUserName = reader.getProperty("Takamol_User_Live");
                        TakamolPasssword = reader.getProperty("Takamol_Passw0rd_Live");

                        //  Portal
                        OcpBaseUrl =
                                PortalUserName = reader.getProperty("");
                        PortalPassword = reader.getProperty("");
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
                    .auth().preemptive().basic(TakamolUserName, TakamolPasssword)
                    .log().all()
                    .post(TakamolBaseUrl + reader.getProperty("Takamol_Path"))
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
            for (Map<String, String> CRData : datatable) {
                CRnumber = CRData.get("CR-Number");
                ThirdpartyName = CRData.get("ThirdPartyName");
            }

            switch (ThirdpartyName) {
                case "takamol":
                    response = given()
                            .relaxedHTTPSValidation()
                            .queryParam("CRNumber", CRnumber)
                            .headers("Authorization", "Bearer " + Token)
                            .log().all()
                            .get(TakamolBaseUrl + reader.getProperty("CRinquiryPath"))
                            .then().log().all().extract().response();
                    Assert.assertTrue(response.getStatusCode() == 200, "GetCRinformation API didn't pass");
                    break;

                case "OcpPortal":
                    response = given()
                            .relaxedHTTPSValidation()
                            .headers("Authorization", "Bearer " + OCPToken)
                            .log().all()
                            .get(OcpBaseUrl + reader.getProperty("Ocp-inquiryPath") + CRnumber
                                    + "/"+reader.getProperty("InquiryReason"))
                            .then().log().all().extract().response();
                    break;
            }
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

    @And("validate that inquiry logged on DB log table")
    public void ReturnCrInquiryNoLoggedOnDb() throws Exception {
        try {
            obj.connectWithMojDB(Env);
        } catch (
                Exception e) {
            throw new Exception("Failure during validate CrInquiry Response ");
        }
    }

    @And("Verify that CR number {string} logged in DB")
    public void VerifyThatCRNumberInquiredByIsWhatloggedInDB(String CR_number) throws Exception {
        try {
            Assert.assertEquals(CR_number, reader.getProperty("CRnumberFromDB"));
        } catch (Exception e) {

            throw new Exception("Failure during validate CrInquiry Response ");
        }
    }

    //OCP
    @And("Authenticate user")
    public void Authenticate(List<Map<String, String>> datatable) {
        try {
            for (Map<String, String> UserCredentials : datatable) {
                PortalUserName = UserCredentials.get("adUserName");
                PortalPassword = UserCredentials.get("adUserPassword");
                response = given()
                        .and().relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .log().all()
                        .body(Payloads.AuthenticateApiPayload(PortalUserName, PortalPassword))
                        .post(OcpBaseUrl+reader.getProperty("AuthenticateApiPath"))
                        .then().log().all().extract().response();
                Assert.assertTrue(response.getStatusCode() == 200);
                Eotp = response.jsonPath().get("data.eotp").toString();
                log.info("eotp is" + response.jsonPath().get("data.eotp").toString());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @And("validate user")
    public void ValidateUser(List<Map<String, String>> datatable) {
        try {
            for (Map<String, String> UserCredentials : datatable) {
                otp = UserCredentials.get("otp");
                response = given()
                        .and().relaxedHTTPSValidation()
                        .header("Content-Type", "application/json")
                        .log().all()
                        .body(Payloads.ValidateOtpPayload(PortalUserName, Eotp, otp))
                        .post(OcpBaseUrl + reader.getProperty("ValidateApi_Path"))
                        .then().log().all().extract().response();
                Assert.assertTrue(response.getStatusCode() == 200);
                OCPToken = response.jsonPath().get("data.token").toString();
                log.info("Portal Token is" + response.jsonPath().get("data.token").toString());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}

