package domain.hollywood;

public class Mallard extends Duck{
    public void fly(){
        super.fly();
    }
    public static void printFly(String input){
        System.out.println(input);
    }

    public void makeNoise(){
        System.out.println("QUACK!");
    }

}
