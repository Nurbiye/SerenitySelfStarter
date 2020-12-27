package serenity.steps;

import net.thucydides.core.annotations.Step;

public class B20Actions {

    @Step
    public void preparedSomething(){
        System.out.println("preparing cool stuff");
    }
    @Step
    public void takeAnAction(){
        System.out.println("taking some action");
    }
    @Step
    public void expectSomeResult(){
        System.out.println("expecting some result");
    }



}
