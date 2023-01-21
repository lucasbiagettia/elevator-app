package ar.org.lbiagetti.app.elevator.elevator_manager;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;

public interface ICallerObserver {

	void goUp(AbstractElevator elevator, Floor floor);

	void goDown(AbstractElevator elevator, Floor floor);
}
