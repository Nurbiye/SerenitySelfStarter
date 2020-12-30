package serenity;


import net.serenitybdd.junit5.SerenityTest;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import serenity.steps.B20Actions;

@SerenityTest
@Tag("voila")
public class VoilaTest {

    @Steps
    B20Actions b20user;

    @Test
    public void testVoila(){
        //B20User preparedSomething
        //B20User takeAnAction
        //B20User expectSomeResults

        //GIVEN
        b20user.preparedSomething();
        //WHEN
        b20user.takeAnAction();
        //THEN
        b20user.expectSomeResult();






    }


}
