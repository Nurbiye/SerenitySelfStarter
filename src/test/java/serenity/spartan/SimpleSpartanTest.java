package serenity.spartan;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serenity.utility.SpartanUtil;

//import static io.restassured.RestAssured.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

@SerenityTest
public class SimpleSpartanTest {
    @BeforeAll
    public static void setup(){
       RestAssured.baseURI = "http://52.90.55.156:8000";
       RestAssured.basePath="/api";
    }
    @AfterAll
    public static void cleanUp(){
        SerenityRest.clear();
      RestAssured.reset();
    }

    @DisplayName("Testing GET/api/hello endpoint")
    @Test
    public void testingHelloEndPoint(){
   given().auth().basic("admin","admin").accept(ContentType.TEXT).
        when().get("/hello")
//         .then().statusCode(200).
//                contentType(ContentType.TEXT)
//                .body(is("Hello from sparta"))
        ;

        //Serenity's way of generating some steps for verification
        // in the report using Ensure class:
//        Ensure.that("Make sure endpoint works" ,
//                response -> response.statusCode(200)
//                        .contentType(ContentType.TEXT)
//                        .body( is("Hello from Sparta") )
//        );
//        //I can comment out above then(). session


        Ensure.that("Success response was received",
                thenResponse -> thenResponse.statusCode(200))
                .andThat("I got text response",
                        blablaResponse -> blablaResponse.
                        contentType(ContentType.TEXT) )
                .andThat("I got Hello from Sparta" ,
                        vResponse -> vResponse.body( is("Hello from Sparta") ) )
                .andThat("I got my response within 5 seconds",
                        vResponse -> vResponse.time( lessThan(5L), TimeUnit.SECONDS))
        ;

    }


}
