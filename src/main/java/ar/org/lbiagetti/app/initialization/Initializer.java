package ar.org.lbiagetti.app.initialization;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorCaller;
import ar.org.lbiagetti.app.elevator.ElevatorFactory;
import ar.org.lbiagetti.app.elevator.elevator_manager.ElevatorManager;
import ar.org.lbiagetti.app.elevator.elevator_manager.KeyBoard;
import ar.org.lbiagetti.app.security_systems.KeyManager;

public class Initializer {

	public Initializer() {
	}

	public void initialize(Building building) {
		if (building.isInitialized()) {
			throw new RuntimeException("The building is initialized");
		}
		Floor initialFloor = new Floor(building, 0, "Floor base level");
		for (int i = 0; i < StaticConfiguration.numberOfFloors; i++) {
			Floor floor;
			if (i == 0) {
				floor = initialFloor;
			} else {
				floor = new Floor(building, i, "Floor " + i);
			}
			building.addFloor(floor);
		}
		for (int j = 0; j < StaticConfiguration.numberOfBasements; j++) {
			Floor floor = new Floor(building, -(j + 1), "Basement " + (j + 1));
			building.addFloor(floor);
		}
		ElevatorFactory elevatorFactory = new ElevatorFactory();
		AbstractElevator publicElevator = elevatorFactory.getPublicElevator();
		AbstractElevator freightElevator = elevatorFactory.getFreightElevator();
		building.addElevator(publicElevator);
		building.addElevator(freightElevator);
		ElevatorManager elevatorManager = building.getElevatorManager();
		publicElevator.addKeyBoard(new KeyBoard(publicElevator, elevatorManager));
		freightElevator.addKeyBoard(new KeyBoard(publicElevator, elevatorManager));
		publicElevator.setInitialFloor(initialFloor);
		publicElevator.setInitialFloor(initialFloor);
		List<AbstractElevator> elevators = building.getElevators();
		List<Floor> floors = building.getFloors();
		List<Floor> restrictedFloor = getRestrictedFloor(floors, StaticConfiguration.restrictedFloors);
		KeyManager.setForbiddenFloors(restrictedFloor);
		for (AbstractElevator abstractElevator : elevators) {
			for (Floor floor: floors) {
				floor.addElevatorCaller(abstractElevator);
				ElevatorCaller elevatorCaller = floor.getElevatorCaller(abstractElevator);
				elevatorCaller.addObserver(elevatorManager);
			}			
		}
		building.initialize();
	}
	
	public List<Floor> getRestrictedFloor(List<Floor> floors, List<Integer> list){
		List<Floor> restrictedFloors = new ArrayList();
		for (Floor floor : floors) {
			if (list.contains(floor.getNumberOfFloor())) {
				restrictedFloors.add(floor);
			}
		}
		return restrictedFloors;
		
	}
}
