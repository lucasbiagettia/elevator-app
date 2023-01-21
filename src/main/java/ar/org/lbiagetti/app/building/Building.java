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
	private Map<Human, Floor> hostsMap;
	// TODO podrían ser hashmaps y verificamos si ya está en el edificio

	public Building() {
		initialized = false;
		floors = new ArrayList();
		hostsMap = new HashMap();
		elevatorManager = new ElevatorManager();
	}
	
	public List<AbstractElevator> getElevators() {
		return elevatorManager.getElevators();
	}
	
	public List <Floor> getFloors(){
		return floors;
	}

	public void addElevator(AbstractElevator theElevator) {
		elevatorManager.addElevator(theElevator);
		elevatorManager.setBuilding(this);
	}

	public void addFloor(Floor theFloor) {
		floors.add(theFloor);
		// TODO sería improtante ordenarlos
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void initialize() {
		initialized = true;
	}

	public void newHost(Human theHuman) {
		hostsMap.put(theHuman, floors.get(0));
	}

	public Floor getCurrentFloorOfUser(Human human) {
		if (hostsMap.containsKey(human)) {
			return hostsMap.get(human);
		}
		else {
			throw new RuntimeException("No esta en el edificio");
			// TODO
		}
	}
}
