package ar.org.lbiagetti.app.elevator;

public class FreightElevator extends AbstractElevator {
	private int maxWeight = 3000;

	@Override
	protected int getMaxWeight() {
		return maxWeight;
	}

}
