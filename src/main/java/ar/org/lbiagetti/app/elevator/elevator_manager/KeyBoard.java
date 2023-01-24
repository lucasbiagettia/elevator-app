package ar.org.lbiagetti.app.elevator.elevator_manager;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;

public class KeyBoard {
	AbstractElevator elevator;
	ElevatorManager elevatorManager;
	
	public KeyBoard(AbstractElevator theElevator, ElevatorManager theElevatorManager) {
		elevator = theElevator;
		elevatorManager = theElevatorManager;
	}

	public void goToFloor(Floor floor) {
		elevatorManager.goToFloor(floor, elevator);
	}

	public void sendAlarm(AbstractElevator abstractElevator) {
		elevatorManager.alarm(abstractElevator);		
	}
}
