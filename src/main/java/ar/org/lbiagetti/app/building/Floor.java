package ar.org.lbiagetti.app.building;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorCaller;

public class Floor {
	final Building building;
	final Integer numberOfFloor;
	final String token;
	private List<ElevatorCaller> elevatorCallers;

	public Floor(Building theBuiding, Integer theNumberOfFloor, String theToken) {
		building = theBuiding;
		numberOfFloor = theNumberOfFloor;
		token = theToken;
		elevatorCallers = new ArrayList<ElevatorCaller>();
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

	public void addElevatorCaller(AbstractElevator elevator) {
		elevatorCallers.add(new ElevatorCaller(elevator, this));
	}

	public ElevatorCaller getElevatorCaller(AbstractElevator elevator) {
		for (ElevatorCaller elevatorCaller : elevatorCallers) {
			if (elevatorCaller.getElevator().equals(elevator)) {
				return elevatorCaller;
			}
		}
		throw new RuntimeException("Ese elevador no para en este piso");
		// TODO mensaje;
	}

}
