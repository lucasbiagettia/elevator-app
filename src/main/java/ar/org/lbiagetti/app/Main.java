package ar.org.lbiagetti.app;

import java.util.List;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.initiaization.Initializer;
import ar.org.lbiagetti.app.security_systems.KeyManager;
import ar.org.lbiagetti.app.users.Human;

public class Main {
	// TODO en los pisos superior e inferior noe xiste hacia arriba y hacia abajo

	public static void main(String[] args) {
		Building building = new Building();
		Initializer initializer = new Initializer();
		initializer.initialize(building);
		Human human = new Human();
		KeyManager.giveKey(human);
		human.enterTheBuilding(building);
		List<AbstractElevator> elevators = building.getElevators();
		AbstractElevator abstractElevator = elevators.get(0);
		human.askToGoUp(abstractElevator); // TODO tiene que poder elegir el elevador
	}
}
