package serenity.library_app;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.*;
import serenity.utility.ConfigReader;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

@SerenityTest
public class LibraryTest {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI  = ConfigReader.getProperty("base.url") ;
        RestAssured.basePath = ConfigReader.getProperty("base.path") ;
    }
    @AfterAll
    public static void tearDown(){
        RestAssured.reset();
        SerenityRest.clear();
    }

    @Test
    public void testingReadingConfigFile(){

        System.out.println("conf.getProperty(\"base.url\") = "
                + ConfigReader.getProperty("base.url"));
        System.out.println("conf.getProperty(\"librarian.username\") = "
                + ConfigReader.getProperty("librarian.username"));

    }

    @Test
    public void testLogin(){
        given()
                .log().all()
                .contentType( ContentType.URLENC  )
                .formParam("email", ConfigReader.getProperty("librarian.username"))
                .formParam("password",ConfigReader.getProperty("librarian.password")).
                when()
                .post("/login").prettyPeek().
                then()
                .statusCode(200)
        ;

        Ensure.that("Getting Successful Result",
                vRes -> vRes.statusCode(200)
        ).andThat("token is not null",
                vRes -> vRes.body("token", is(notNullValue()))
        ) ;

    }

    @DisplayName("Get Dashboard Stats Test")
    @Test
    public void zDashboardStatTest(){
        // better way is creating utility to get the token
        // and call it under before all
        // we will just manipulate the order here by adding z in front of the test
        String token = lastResponse().jsonPath().getString("token") ;
        given()
                .header("x-library-token", token).
                when()
                .get("/dashboard_stats").prettyPeek() ;

        Ensure.that("Successful result was received",
                vRes-> vRes.statusCode( is(200) ))
                .andThat("All 3 fields are not null",
                        vRes-> vRes.body("book_count", notNullValue())
                                .body("borrowed_books", notNullValue())
                                .body("users", notNullValue())
                );

        //TODO : Get the expected data from database
        //TODO : Ensure that the results match

    }


}