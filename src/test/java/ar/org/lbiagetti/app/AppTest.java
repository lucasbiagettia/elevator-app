package ar.org.lbiagetti.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.initiaization.Initializer;
import ar.org.lbiagetti.app.initiaization.Logger;
import ar.org.lbiagetti.app.initiaization.StaticConfiguration;
import ar.org.lbiagetti.app.security_systems.KeyManager;
import ar.org.lbiagetti.app.users.Human;

public class AppTest {
	@Test
	public void almostAnE2e() {

		Building building = new Building();
		Initializer initializer = new Initializer();
		initializer.initialize(building);
		StaticConfiguration.debugLogger = true;
		Human human = new Human();
		KeyManager.giveKey(human);
		human.enterTheBuilding(building);
		List<AbstractElevator> elevators = building.getElevators();
		AbstractElevator elevator = elevators.get(0);
		human.callAndWaitThe(elevator, Direction.GO_UP);
		assertEquals(elevator.getQueueForEdit().size(), 0);
		assertEquals(elevator.getCurrentFloor(), human.getOptionalFloor().get());

	}
}
