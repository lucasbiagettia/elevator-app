package ar.org.lbiagetti.app.building;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorManager;

public class Building {
	private List <Floor> floors;
	private ElevatorManager elevatorManager;
	//TODO podrían ser hashmaps y verificamos si ya está en el edificio
	
	public Building() {
		floors = new ArrayList();
	}
	
	public void addElevator (AbstractElevator theElevator) {
		elevatorManager.addElevator(theElevator);
		elevatorManager.setBuilding(this);
	}
	
	public void addFloor(Floor theFloor) {
		floors.add(theFloor);
		//TODO sería improtante ordenarlos
	}
	
	
}
