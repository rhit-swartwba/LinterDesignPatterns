package domain.decorator.badimplementation;

public class Whip extends Beverage {
	Beverage beverage;

	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	public double cost() {
		return .40 + beverage.cost();
	}
}
