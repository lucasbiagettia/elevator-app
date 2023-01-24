package ar.org.lbiagetti.app.elevator;

import ar.org.lbiagetti.app.building.Floor;

public interface IElevatorUser {

	void theElevatorIsArrived(AbstractElevator elevator);

	void setNewFloor(Floor floor);

	int getWeight();
	
}
