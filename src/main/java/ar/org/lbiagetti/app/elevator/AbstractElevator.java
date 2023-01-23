package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.KeyBoard;

public abstract class AbstractElevator {
	//TODO inicializar variables en los constructore
	private ElevatorStatus status;
	private Map<Floor, List<IElevatorUser>> mapToNotify;
	private List<IElevatorUser> elevatorUsers;
	private KeyBoard keyBoard;

	public void notifyMe(Floor floor, IElevatorUser elevatorUser) {
		if (mapToNotify.containsKey(floor)) {
			mapToNotify.get(floor).add(elevatorUser);
		}
		else {
			mapToNotify.put(floor, Arrays.asList(elevatorUser));
		}
	}
	
	private void arriveToANewFloor(Floor floor) {
		status = ElevatorStatus.STOPPED;
		if (mapToNotify.containsKey(floor)) {
			List<IElevatorUser> list = mapToNotify.get(floor);
			mapToNotify.remove(list);
			for (IElevatorUser iElevatorUser : list) {
				iElevatorUser.theElevatorIsArrived(this);
			}
		}
	}
	
	// This method must be sincrhonized because two users can try to invoke this
	public synchronized boolean addNewUser(IElevatorUser theUser) {
		if (!isValidUser()) {
			return false;
		}else {
			elevatorUsers.add(theUser);
			return true;
		}
	}
	
	abstract boolean isValidUser();

	public KeyBoard getKeyBoard() {
		return keyBoard;
	}
}
