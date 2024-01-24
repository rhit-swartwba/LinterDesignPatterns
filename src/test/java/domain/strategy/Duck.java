package domain.strategy;

public class Duck {
FlyBehavior flyBehavior;
QuackBehavior quackBehavior;

public void setFlyBehavior(FlyBehavior fly){
    this.flyBehavior = fly;
}

public void setQuackBehavior(QuackBehavior quack){
    this.quackBehavior = quack;
}

public void printBehaviors(){
    flyBehavior.fly();
    quackBehavior.makeNoise();
}
}
