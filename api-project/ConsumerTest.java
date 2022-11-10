package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Create Headers
    Map<String,String> headers=new HashMap<>();
    //Resource Path
    String resourcePath="/api/users";
    //Create contract
    @Pact(consumer="UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Add Headers
        headers.put("Content-Type", "application/json");
        //Create request and response body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Maha")
                .stringType("lastName", "Lakshmi")
                .stringType("email", "maha@mail.com");
        //Record Interaction to Pact
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(resourcePath)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
    }
    //Test with Mock Provider
    @Test
    @PactTestFor(providerName = "UserProvider", port="8282")
    public void consumerTest(){
        Map<String,Object> reqBody=new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName","Maha");
        reqBody.put("lastName","Lakshmi");
        reqBody.put("email","maha@mail.com");

        //Generate response
        given().headers(headers).body(reqBody).log().all()
                .when().post("http://localhost:8282/api/users").then().statusCode(201).log().all();

        }
    }

