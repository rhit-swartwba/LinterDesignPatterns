package domain.decorator.badimplementation;

public class Mocha extends Beverage {
	Beverage beverage;

	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}
 
	public String getDescription() {
		return beverage.getDescription() + ", Mocha";
	}
 
	public double cost() {
		return .30 + beverage.cost();
	}
}
