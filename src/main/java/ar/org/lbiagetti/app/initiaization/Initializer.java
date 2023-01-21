package ar.org.lbiagetti.app.initiaization;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorFactory;

public class Initializer {

	public Initializer() {
	}

	public void initialize(Building building) {
		if (building.isInitialized()) {
			throw new RuntimeException("The building is initialized");
		}
		for (int i = 0; i < StaticConfiguration.numberOfFloors; i++) {
			Floor floor;
			if (i == 0) {
				floor = new Floor(building, i, "Floor base level");

			} else {
				floor = new Floor(building, i, "Floor " + i);
			}
			building.addFloor(floor);
		}
		for (int j = 0; j < StaticConfiguration.numberOfBasements; j++) {
			Floor floor = new Floor(building, -(j + 1), "Basement " + (j + 1));
		}
		ElevatorFactory elevatorFactory = new ElevatorFactory();
		AbstractElevator publicElevator = elevatorFactory.getPublicElevator();
		building.addElevator(publicElevator);
		AbstractElevator freightElevator = elevatorFactory.getFreightElevator();
		building.addElevator(freightElevator);
		building.initialize();
	}
}
