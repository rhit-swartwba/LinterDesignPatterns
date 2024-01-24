package domain.decorator.goodimplementation;

public class Coffee extends Beverage {

    public Coffee() {
        this.description = "Coffee";
    }
    @Override
    public double cost() {
        return 4.50;
    }

}
