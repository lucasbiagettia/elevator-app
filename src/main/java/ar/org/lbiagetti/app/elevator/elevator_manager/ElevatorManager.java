package ar.org.lbiagetti.app.elevator.elevator_manager;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;

public class ElevatorManager implements ICallerObserver {
	private List <AbstractElevator> elevators;
	private Building building;
	
	public ElevatorManager () {
		elevators = new ArrayList();
	}

	@Override
	public void goUp(Floor step) {
		throw new RuntimeException("implementar metodo");		
	}

	@Override
	public void goDown(Floor step) {
		throw new RuntimeException("implementar metodo");		
	}

	public void addElevator(AbstractElevator theElevator) {
		elevators.add(theElevator);
	}

	public void setBuilding(Building theBuilding) {
		building = theBuilding;		
	}

	public List <AbstractElevator> getElevators() {
		return elevators;
	}
	
}
