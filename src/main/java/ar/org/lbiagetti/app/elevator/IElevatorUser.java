package ar.org.lbiagetti.app.elevator;

import java.util.Optional;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.security_systems.Key;

public interface IElevatorUser {

	public void enterTheBuilding(Building building);

	public Optional<Building> getOptionalBuilding();

	public void callAndWaitThe(AbstractElevator abstractElevator, Direction theDirection);

	public void theElevatorIsArrived(AbstractElevator elevator);

	public void setNewFloor(Floor floor);

	public int getWeight();

	public Key getKey();

	public void giveKey(Key theKey);

}
