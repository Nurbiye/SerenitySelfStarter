package serenity.spartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import serenity.utility.SpartanUtil;

import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

@SerenityTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class SpartanAdminCRUD {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://52.90.55.156:8000";
        RestAssured.basePath="/api";
        //this is setting static field of rest assured class requestSpecification
        //to the value we specified
        //so it become global whatever test in this class can use it
       // RestAssured.requestSpecification = given().auth().basic("admin","admin");
                                    //opps , this given is from serenity, so we can't use in this way

    }
    @AfterAll
    public static void cleanUp(){
        SerenityRest.clear();
        RestAssured.reset();
    }


    @DisplayName("1.Admin user should be able to Add Spartan")
    @Test
    public void testAdd1Data(){
        Map<String, Object> payload = SpartanUtil.getRandomSpartanRequestPayload();

        given()
                .auth().basic("admin","admin")
                // -->we specified it in BeforeAll Specification(yes, we were thinking like that before,
  // but the given is not from RestAssured, is from serenity, so requestSpecification might be not a good option)
                .log().body()
                .contentType(ContentType.JSON)
                .body(payload).
                when().post("/spartans");

        Ensure.that("Request was successful",
                thenResponse ->thenResponse.statusCode(201) )
                . andThat("We got json format result",
                        thenResponse -> thenResponse.contentType(ContentType.JSON) )
                .andThat("success message is A Spartan is born!",
                        thenResponse -> thenResponse.body("success",is
                                ("A Spartan is Born!")) )
        ;

        Ensure.that("The data "+payload+" we provided added correctly",
                vRes -> vRes.body("data.name", is( payload.get("name")  ) )
                        .body("data.gender", is( payload.get("gender")  ) )
                        .body("data.phone", is( payload.get("phone")  ) ) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue() )))    ) ;

        //how do we extract information after sending request?
        //for example I want to print out ID,
        // lastResponse()-->coming from serenityRest class,
        // and contain the Response object obtained from last ran request.

        //lastResponse().prettyPeek();
        System.out.println("lastResponse().jsonPath().getInt(\"data.id\") = "
                + lastResponse().jsonPath().getInt("data.id"));

    }


    @DisplayName("2.Admin should be able to read single data")
    @Test
    public void getOneData(){
     int newID = lastResponse().jsonPath().getInt("data.id");
       // System.out.println("newID = " + newID);
                //I can give the auth().basic()--> basic auth part at BeforeAll part, "request specification"
        given().auth().basic("admin","admin").
        when().get("/spartans/{id}", newID);
        Ensure.that("We can access newly generated data",
                thenResponse-> thenResponse.statusCode(200) ) ;
        //add all validation for body here as homework
        //add put and patch as homework
    }

    @DisplayName("3.Admin should be able to delete single data")
    @Test
    public void testDeleteOneData() {

        //capture the ID from last get request
        int myId = lastResponse().jsonPath().getInt("id");

        given().auth().basic("admin","admin")
                .pathParam("id",myId)
                .when().delete("/spartans/{id}");

        Ensure.that("Request is successful",
                thenRequest -> thenRequest.statusCode(204) );

        //send another get request to make sure you get 404

        given().
                auth().basic("admin","admin")
                .pathParam("id",myId)
                .when().get("/spartans/{id}");

        Ensure.that("Delete was successful, Can not find data anymore",
                vReq->vReq.statusCode(404));

    }


}
