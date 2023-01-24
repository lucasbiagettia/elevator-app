package ar.org.lbiagetti.app.elevator.elevator_manager;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.IElevatorUser;

public class KeyBoard {
	AbstractElevator elevator;
	ElevatorManager elevatorManager;
	
	public KeyBoard(AbstractElevator theElevator, ElevatorManager theElevatorManager) {
		elevator = theElevator;
		elevatorManager = theElevatorManager;
	}

	public void goToFloor(Floor floor, IElevatorUser elevatorUser) throws ElevatorException {
		elevatorManager.goToFloor(floor, elevator, elevatorUser);
	}

	public void sendAlarm(AbstractElevator abstractElevator) {
		elevatorManager.alarm(abstractElevator);		
	}
}
