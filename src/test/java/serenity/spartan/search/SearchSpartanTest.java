package serenity.spartan.search;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serenity.utility.SpartanTestBase;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.*;


@SerenityTest
public class SearchSpartanTest extends SpartanTestBase {

    @DisplayName("Authenticated user should be able to search")
    @Test
    public void testSearch(){
        given().auth().basic("admin","admin").
                queryParam("nameContains","a")
                .queryParam("gender","Male")
                .when().get("/spartans/search");

        Ensure.that("Request was Successful",
                thenResponse -> thenResponse.statusCode(200) ).
                andThat("We got JSON Result",
                        vRes->vRes.contentType(ContentType.JSON) );

        Ensure.that("Make sure every item actually contains a ",
                vRes -> vRes.body("content.name",
                        everyItem(anyOf(containsString("a"),containsString("A"))) ) )
        .andThat("every item gender value is Male",
                vRes -> vRes.body("content.gender", everyItem( is("Male") ) ) )
        ;

    }

}
