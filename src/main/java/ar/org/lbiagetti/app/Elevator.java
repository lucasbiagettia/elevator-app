package ar.org.lbiagetti.app;

public abstract class Elevator {
	private int weightLimit;
	private boolean basementAccess;
	private int currentFloor;
	private int weightInside;
	private boolean alarmActive;

	public Elevator(int weightLimit, boolean basementAccess) {
		this.weightLimit = weightLimit;
		this.basementAccess = basementAccess;
		this.currentFloor = 0;
		this.weightInside = 0;
		this.alarmActive = false;
	}

	public void moveToFloor(int floor) {
		if (floor >= 0 && floor <= 50) {
			this.currentFloor = floor;
		} else {
			System.out.println("Invalid floor number");
		}
	}

	public void loadWeight(int weight) {
		if (this.weightInside + weight > this.weightLimit) {
			this.alarmActive = true;
			System.out.println("Weight limit exceeded");
		} else {
			this.weightInside += weight;
		}
	}

	public void unloadWeight(int weight) {
		this.weightInside -= weight;
		if (this.weightInside <= 0) {
			this.alarmActive = false;
		}
	}
}
