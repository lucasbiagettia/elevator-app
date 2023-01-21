package ar.org.lbiagetti.app.elevator.elevator_manager;

import ar.org.lbiagetti.app.building.Floor;

public interface ICallerObserver {

	void goUp(Floor step);

	void goDown(Floor step);

}
