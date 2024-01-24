package domain.decorator.goodimplementation;

public abstract class CondimentDecorator extends Beverage {
	Beverage beverage;

	public abstract String getDescription();
}
