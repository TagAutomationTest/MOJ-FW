package Helpers;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;



public class Payloads {
    Map<String, String> WhiteListPayload;
    String CompanyName;
    String Nationality;
    String legalTypeID;
    String PartyTypeId;
    String LegalTypeAR;
    String CRStatusReasonAr;
    String RequiredAction;
    String ExpirationDate;
    String OcpRequestId;
    String IdentityId;
    String reportId;

    public static String AuthenticateApiPayload(String UserName ,String password){
        return "{\n" +
                "\"adUserName\":  \"" + UserName + "\"" +
                ",\"adUserPassword\": \"" + password + "\"" +
                " }";
    }


public static String ValidateOtpPayload(String UserName,String Eotp ,String otp ){
    return "{\n" +
            " \"eOtp\":\"" + Eotp + "\"" +
            "  ,\"otp\":\"" + otp + "\"" +
            "  ,\"username\": \"" + UserName + "\"" +
            " }";
    }

    public static String OCPLoginBody(String UserName){
        return
                "{\n" +
                        " \"adUserName\":" + UserName + "\r\n" +
                        "  \"adUserPassword\": \"1234\"\n" +
                        "}";

    }
    public static String OCPLValidateBody(String UserName,String eotp){
        return "{\n" +
                "  \"otp\": \"1234\",\n" +
                "  \"eOtp\":"+ eotp + "\r\n" +
                " \"username\":" + UserName + "\r\n" +
                "}";
    }

public JSONObject ReadJson() throws IOException, ParseException {
    try {
    JSONParser parser = new JSONParser();
    String FilePath = System.getProperty("user.dir") + "/src/test/testdata/TestData.Json";
    Object obj = parser.parse(new FileReader(FilePath));
    JSONObject jsonObj = new JSONObject((Map) obj);
    return jsonObj;
    } catch (Exception e) {
        throw e;
    }
}
}

