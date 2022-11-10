package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GitHub_RestAssured_Project {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String SSHkey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDH6AQUBV4V5vH2qwhAAKiaZKX9eZuCqY3piDPPTF7M2mWFvInZM5YFXGB2TGUqknWcBkElqCr5NHGy5ESgEp2dPoDr6mNrH3p6Rm51XsZ6p6TZRRA1rxUI7ty09S/dHIbi+xOMuyWGANFlvG1eN9C0u+HM/BN6/1r2iiag1x7ZEmSErtvVKMx3xzSW3CwBuYJnkHCKKVX+68x3YmOVDZaC/2nl596jwRgkEcpDnpcIrr8R55bmXcgamhdu3mwGiGMlRDokn21as5Ij1E8gA1whGxx0OUDL5nxQWaYZCAb8JgFRJYngG0JYcWyA1QEpDGEtXYwh7Q+dWViKY52qnZbd";
    int keyId;

    @BeforeClass
    public void setUp(){
        requestSpecification=new RequestSpecBuilder().setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .build();
    }
    @Test(priority = 1)
    public void postRequest(){
        Map<String,Object> reqBody=new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key",SSHkey);
        Response response=given().spec(requestSpecification).body(reqBody)
                .when().post("/user/keys");
        //System.out.println(response.getBody().asPrettyString());
        keyId=response.then().extract().path("id");
        response.then().statusCode(201);

    }
    @Test(priority = 2)
    public void getRequest(){
        Response response=given().log().body().spec(requestSpecification).pathParam("keyId",keyId)
                .when().get("/user/keys/{keyId}");
        response.then().log().body().statusCode(200);

    }
    @Test(priority = 3)
    public void deleteRequest(){
        Response response=given().log().body().spec(requestSpecification).pathParam("keyId",keyId)
                .when().delete("/user/keys/{keyId}");
        response.then().log().body().statusCode(204);

    }
}
