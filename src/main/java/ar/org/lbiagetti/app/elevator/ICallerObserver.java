package ar.org.lbiagetti.app.elevator;

import ar.org.lbiagetti.app.building.Floor;

public interface ICallerObserver {

	void goUp(Floor step);

	void goDown(Floor step);

}
