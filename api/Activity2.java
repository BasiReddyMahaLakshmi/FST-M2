package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void addNewUser() throws IOException {
        File file = new File("src/test/java/activities/input.json");
        FileInputStream inputJSON = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        inputJSON.read(bytes);
        String reqBody = new String(bytes, "UTF-8");
        System.out.println(reqBody);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(reqBody)
                .when().post(ROOT_URI);
        String body = response.getBody().asPrettyString();
        System.out.println(body);
        inputJSON.close();
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("17041997"));
    }

    @Test(priority = 2)
    public void getUserByUsername() {
        File outputJSON = new File("src/test/java/activities/response.json");
        Response response = given().contentType(ContentType.JSON)
                        .pathParam("username", "Lakshmi")
                        .when().get(ROOT_URI + "/{username}");
        String resBody = response.getBody().asPrettyString();
        try {
            outputJSON.createNewFile();
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        response.then().body("id", equalTo(17041997));
        response.then().body("username", equalTo("Lakshmi"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));
    }
    @Test(priority = 3)
    public void deleteUserByUsername(){
        Response response=given().contentType(ContentType.JSON)
                .when().pathParam("username", "Lakshmi")
                .delete(ROOT_URI + "/{username}");
        response.then().body("code", equalTo(200));
        response.then().body("message",equalTo("Lakshmi"));
    }


}


