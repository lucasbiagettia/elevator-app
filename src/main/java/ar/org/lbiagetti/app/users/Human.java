package ar.org.lbiagetti.app.users;

import java.util.List;
import java.util.Optional;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorCaller;
import ar.org.lbiagetti.app.elevator.IElevatorUser;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.elevator.elevator_manager.ElevatorException;
import ar.org.lbiagetti.app.elevator.elevator_manager.KeyBoard;
import ar.org.lbiagetti.app.initialization.Logger;
import ar.org.lbiagetti.app.security_systems.Key;

public class Human implements IElevatorUser {
	// TODO despues vemos que ahcemos con las interfaces y clases abstractas
	private Optional<Building> optionalBuilding = Optional.empty();
	private Optional <Floor> optionalFloor = Optional.empty();
	private int weight = 200;
	private Key key;

	public Optional<Floor> getOptionalFloor() {
		return optionalFloor;
	}

	public void enterTheBuilding(Building building) {
		optionalBuilding = Optional.of(building);
		optionalFloor = Optional.of(building.getFirstFloor());
		Logger.log("Entra el edificio el usuario " + this.toString(), true);
	}
	
	public Optional<Building> getOptionalBuilding() {
		return optionalBuilding;
	}
	
	// TODO este método debería estar en la interfaz
	public void callAndWaitThe(AbstractElevator abstractElevator, Direction theDirection) {
		Logger.log("Lllama al ascensor " + abstractElevator.toString()+ "El usuario " + this.toString() +" con direccion " + theDirection, true);
		ElevatorCaller elevatorCaller = getElevatorCaller(abstractElevator);
		elevatorCaller.call(theDirection);
		abstractElevator.notifyMe(optionalFloor.get(), this);
	}
	
	private ElevatorCaller getElevatorCaller(AbstractElevator abstractElevator) {
		if (optionalFloor.isPresent()) {
			Floor floor = optionalFloor.get();
			ElevatorCaller elevatorCaller = floor.getElevatorCaller(abstractElevator);
			return elevatorCaller;
		}else {
			throw new RuntimeException ("No está en ningún piso");
		}
		
	}

	@Override
	public void theElevatorIsArrived(AbstractElevator elevator) {
		getEnterTheElevator(elevator);
	}

	private void getEnterTheElevator(AbstractElevator elevator) {
		KeyBoard keyBoard = elevator.getKeyBoard();
		try {
		keyBoard.goToFloor(selectRandomFloor(), this);
		} catch (ElevatorException e){
			return;
		}
		elevator.addNewUser(this);
	}
	
	private Floor selectRandomFloor(){
		List<Floor> floors = optionalBuilding.get().getFloors();
		int size = floors.size();
		Floor floor = floors.get((int) (Math.random()*size));
		return floor;
	}
	

	@Override
	public void setNewFloor(Floor floor) {
		optionalFloor = Optional.of(floor);
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void giveKey(Key theKey) {
		key = theKey;
		
	}
}