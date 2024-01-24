package domain.strategy;

public class CannotFly implements FlyBehavior{
    public void fly(){
        System.out.println("No way I can get off the ground!");
    }

}
