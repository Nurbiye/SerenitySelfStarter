package serenity.utility;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SpartanTestBase {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://52.90.55.156:8000";
        RestAssured.basePath="/api";

    }
    @AfterAll
    public static void cleanUp(){
        RestAssured.reset();
        SerenityRest.clear();

    }





}
