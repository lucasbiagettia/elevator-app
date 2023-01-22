package ar.org.lbiagetti.app.users;

import java.util.List;
import java.util.Optional;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorCaller;
import ar.org.lbiagetti.app.elevator.IElevatorUser;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.security_systems.IKeyUser;

public class Human implements IKeyUser, IElevatorUser {
	// TODO despues vemos que ahcemos con las interfaces y clases abstractas
	private Optional<Building> optionalBuilding = Optional.empty();
	private Optional <Floor> optionalFloor = Optional.empty();
	private Optional <Direction> lastDirection;

	public void enterTheBuilding(Building building) {
		optionalBuilding = Optional.of(building);
		optionalFloor = Optional.of(building.getFirstFloor());
	}
	
	public Optional<Building> getOptionalBuilding() {
		return optionalBuilding;
	}
	
	// TODO este método debería estar en la interfaz
	public void callAndEnterElevator(AbstractElevator abstractElevator, Direction theDirection) {
		ElevatorCaller elevatorCaller = getElevatorCaller(abstractElevator);
		elevatorCaller.call(theDirection);
		lastDirection = Optional.of(theDirection);
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
		if (!elevator.addNewUser(this)) {
			callAndEnterElevator(elevator, lastDirection.get());
			List<Floor> floors = optionalBuilding.get().getFloors();
			int size = floors.size();
			Floor floor = floors.get((int) (Math.random()*size));
			chooseFloor(elevator, floor);
		}else {
			callAndEnterElevator(elevator, lastDirection.get());
		}
	}
	
	private void chooseFloor(AbstractElevator theElevator, Floor floor) {
		// TODO en una aplicación real este método debería sobreescribirse y el usuario deberìa pdoer elegirlo
		do {
			KeyBoard keyBoard = elevator.getKeyBoard();
			keyBoard.goToFloor(floor);
		}while (isAvaibleFloor = false)
	}
}