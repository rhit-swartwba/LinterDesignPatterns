package domain.hollywood;

import domain.strategy.FlyBehavior;
import domain.strategy.QuackBehavior;

public class Duck {
    public void fly(){
        if(this.getClass().equals(Mallard.class)){
            Mallard.printFly("I can fly!");
        }
        else if(this.getClass().equals(Baby.class)){
            Baby.printFly("I can't fly I'm too young!");
        }
        else if(this.getClass().equals(Rubber.class)){
            Rubber.printFly("I cant fly I don't have any wings!");
        }
        else{
            System.out.println("I am a normal duck, I can fly!");
        }
    }

    public void makeNoise(String type){
        if(type.equals("Mallard")){
            new Mallard().makeNoise();
        }
        else if(type.equals("Baby")){
            new Baby().makeNoise();
        }
        else if(type.equals("Rubber")){
            new Rubber().makeNoise();
        }
    }
}
