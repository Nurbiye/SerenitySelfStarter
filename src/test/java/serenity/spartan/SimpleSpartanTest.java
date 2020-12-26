package serenity.spartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static net.serenitybdd.rest.SerenityRest.*;

@SerenityTest
public class SimpleSpartanTest {
    @BeforeAll
    public static void setup(){
       RestAssured.baseURI = "http://52.90.55.156:8000";
       RestAssured.basePath="/api";
    }
    @AfterAll
    public static void cleanUp(){
        reset();
    }

    @DisplayName("Testing GET/api/hello endpoint")
    @Test
    public void testingHelloEndPoint(){
        when().get("/spartans").
         then().statusCode(200).
                contentType(ContentType.TEXT)
                .body(is("Hello from sparta"));

        //Serenity's way of generating some steps for verification
        // in the report using Ensure class:
        Ensure.that("Make sure endpoint works" ,
                response -> response.statusCode(200)
                        .contentType(ContentType.TEXT)
                        .body( is("Hello from Sparta") )
        );
        //I can comment out above then(). session


        Ensure.that("Success response was received",
                thenResponse -> thenResponse.statusCode(200))
                .andThat("I got text response",
                        blablaResponse -> blablaResponse.
                                contentType(ContentType.TEXT) );

    }

}
