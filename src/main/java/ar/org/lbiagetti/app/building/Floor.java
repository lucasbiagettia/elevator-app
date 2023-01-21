package ar.org.lbiagetti.app.building;

import ar.org.lbiagetti.app.elevator.ElevatorCaller;

public class Floor {
	final Building building;
	final Integer numberOfFloor;
	final String token;
	final ElevatorCaller elevatorCaller;

	public Floor(Building theBuiding, Integer theNumberOfFloor, String theToken) {
		building = theBuiding;
		numberOfFloor = theNumberOfFloor;
		token = theToken;
		elevatorCaller = new ElevatorCaller(this);
	}

	public Building getBuilding() {
		return building;
	}

	public Integer getNumberOfFloor() {
		return numberOfFloor;
	}

	public String getToken() {
		return token;
	}

	public ElevatorCaller getElevatorCaller() {
		return elevatorCaller;
	}

}
