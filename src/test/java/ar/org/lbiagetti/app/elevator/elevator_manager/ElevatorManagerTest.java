package ar.org.lbiagetti.app.elevator.elevator_manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorStatus;
import ar.org.lbiagetti.app.elevator.FreightElevator;
import ar.org.lbiagetti.app.elevator.PublicElevator;

public class ElevatorManagerTest {

	@Test
	public void testAddElevator() {
		ElevatorManager elevatorManager = new ElevatorManager();
		AbstractElevator elevator = new PublicElevator();
		elevatorManager.addElevator(elevator);
		assertTrue(elevatorManager.getElevators().contains(elevator));
	}

	@Test
	public void testSetBuilding() {
		ElevatorManager elevatorManager = new ElevatorManager();
		Building building = new Building();
		elevatorManager.setBuilding(building);
		assertEquals(building, elevatorManager.getBuilding());
	}

	@Test
	public void testCall() {
		ElevatorManager elevatorManager = new ElevatorManager();
		AbstractElevator elevator = new PublicElevator();
		elevatorManager.addElevator(elevator);
		Building building = new Building();
		elevator.setInitialFloor(new Floor(building, 2, "testFloor"));
		elevator.setElevatorStatus(ElevatorStatus.ASCENDING);
		Floor testFloor = new Floor(building, 5, "test 5");
		elevatorManager.call(elevator, testFloor, Direction.GO_UP);
		assertEquals(testFloor, elevator.getQueueForEdit().get(0));
	}

	@Test
	public void testCall_queueContainsCurrentFloor() {
		Building building = new Building();
		Floor testFloor1 = new Floor(building, 2, "test 5");
		Floor testFloor3 = new Floor(building, 5, "test 5");
		
		ElevatorManager elevatorManager = new ElevatorManager();
		AbstractElevator elevator = new FreightElevator();
		elevatorManager.addElevator(elevator);
		Floor currentFloor = testFloor1;
		elevator.setInitialFloor(currentFloor);
		elevator.setElevatorStatus(ElevatorStatus.ASCENDING);
		elevator.getQueueForEdit().add(currentFloor);
		elevatorManager.call(elevator, testFloor3, Direction.GO_UP);
		assertEquals(1, elevator.getQueueForEdit().size());
	}

}
