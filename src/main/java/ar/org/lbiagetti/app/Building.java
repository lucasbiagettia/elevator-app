package ar.org.lbiagetti.app;

public class Building {
	private int numFloors;
	private Elevator publicElevator;
	private Elevator freightElevator;

	public Building(int numFloors, Elevator publicElevator, Elevator freightElevator) {
		this.numFloors = numFloors;
		this.publicElevator = publicElevator;
		this.freightElevator = freightElevator;
	}
}
