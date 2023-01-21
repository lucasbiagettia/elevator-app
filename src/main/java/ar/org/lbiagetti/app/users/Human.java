package ar.org.lbiagetti.app.users;

import java.util.List;
import java.util.Optional;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorCaller;
import ar.org.lbiagetti.app.security_systems.IKeyUser;

public class Human implements IKeyUser {
	// TODO despues vemos que ahcemos con las interfaces y clases abstractas
	private Optional<Building> optionalBuilding;

	public void enterTheBuilding(Building building) {
		optionalBuilding = Optional.of(building);
		building.newHost(this);
	}
	
	public Optional<Building> getOptionalBuilding() {
		return optionalBuilding;
	}
	
//	public Optional<Floor> getOptionalFloor(){
//		// TODO debería poder retornar en qué piso está, tipo la firma, no el objeto porque no quiero que me lo manejen afuera
//	}
	// TODO es una poc hay que decidir qué métodos van a la interfaz
	public void askToGoUp(AbstractElevator elevator) {
		if (optionalBuilding.isPresent()) {
			Floor floor = optionalBuilding.get().getCurrentFloorOfUser(this);
			ElevatorCaller elevatorCaller = floor.getElevatorCaller(elevator);
			elevatorCaller.goUp();			
			
		}else {
			throw new RuntimeException ("The human must to be in a building");
		}
	}


}