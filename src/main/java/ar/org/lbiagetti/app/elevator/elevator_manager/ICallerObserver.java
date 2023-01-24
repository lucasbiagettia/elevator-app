package ar.org.lbiagetti.app.elevator.elevator_manager;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.IElevatorUser;

public interface ICallerObserver {

	void call(AbstractElevator elevator, Floor floor, Direction direction, IElevatorUser elevatorUser);

}
