package ar.org.lbiagetti.app.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.elevator_manager.ElevatorManager;
import ar.org.lbiagetti.app.users.Human;

public class Building {
	private List<Floor> floors;
	private ElevatorManager elevatorManager;
	private boolean initialized;

	public Building() {
		initialized = false;
		floors = new ArrayList<Floor>();
		elevatorManager = new ElevatorManager();
	}
	
	public List<AbstractElevator> getElevators() {
		return elevatorManager.getElevators();
	}
	
	public List <Floor> getFloors(){
		return floors;
	}
	
	public ElevatorManager getElevatorManager() {
		return elevatorManager;
	}

	public void addElevator(AbstractElevator theElevator) {
		elevatorManager.addElevator(theElevator);
		elevatorManager.setBuilding(this);
	}

	public void addFloor(Floor theFloor) {
		floors.add(theFloor);
		// TODO sería conveniente ordenarlos
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void initialize() {
		initialized = true;
	}

	public Floor getFirstFloor() {
		for (Floor floor : floors) {
			if (floor.getNumberOfFloor() == 0){
				return floor;
			}
		}
		throw new RuntimeException("El piso no existe");
	}
	
	private void callInmediatelyElevator(AbstractElevator elevator, Floor floor) {
		elevatorManager.callInmediately(elevator, floor);
	}
}
