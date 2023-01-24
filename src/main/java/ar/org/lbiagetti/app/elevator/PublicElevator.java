package ar.org.lbiagetti.app.elevator;

public class PublicElevator extends AbstractElevator {
	private int maxWeight = 1000;

	@Override
	protected int getMaxWeight() {
		return maxWeight;
	}

}
