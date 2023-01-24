package ar.org.lbiagetti.app.security_system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.IElevatorUser;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.security_systems.Key;
import ar.org.lbiagetti.app.security_systems.KeyManager;
import ar.org.lbiagetti.app.security_systems.TypeOfKey;

public class KeyManagerTest {

	@Test
    public void testGiveKey() {
        // Create a mock user object
        IElevatorUser mockUser = new IElevatorUser() {
            private Key key;

            @Override
            public void giveKey(Key key) {
                this.key = key;
            }

            @Override
            public Key getKey() {
                return this.key;
            }

			@Override
			public void enterTheBuilding(Building building) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Optional<Building> getOptionalBuilding() {
				// TODO Auto-generated method stub
				return Optional.empty();
			}

			@Override
			public void callAndWaitThe(AbstractElevator abstractElevator, Direction theDirection) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void theElevatorIsArrived(AbstractElevator elevator) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setNewFloor(Floor floor) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getWeight() {
				// TODO Auto-generated method stub
				return 0;
			}
        };

        KeyManager.giveKey(mockUser);

        assertNotNull(mockUser.getKey());
    }

    @Test
    public void testSetForbiddenFloors() {
        List<Floor> floors = new ArrayList<Floor>();
        Building building = new Building();
        floors.add(new Floor(building, 1, "Floor1"));
        floors.add(new Floor(building, 2, "Floor2"));

        KeyManager.setForbiddenFloors(floors);

        assertTrue(KeyManager.getForbiddenFloors().containsAll(floors));
    }
}
