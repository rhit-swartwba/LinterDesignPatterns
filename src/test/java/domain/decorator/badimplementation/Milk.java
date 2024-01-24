package domain.decorator.badimplementation;


public class Milk extends Beverage {

	Beverage beverage;

	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return "Milk";
	}

	public double cost() {
		return .20;
	}
}
