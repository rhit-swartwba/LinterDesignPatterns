package domain.strategy;

public class Quack implements QuackBehavior{
    public void makeNoise(){
        System.out.println("Quack!!!");
    }
}
