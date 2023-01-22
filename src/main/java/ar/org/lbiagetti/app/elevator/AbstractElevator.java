package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.building.Floor;

public abstract class AbstractElevator {
	//TODO inicializar variables en los constructore
	private ElevatorStatus status;
	private Map<Floor, List<IElevatorUser>> mapToNotify;
	private List<IElevatorUser> elevatorUsers;

	public void notifyMe(Floor floor, IElevatorUser elevatorUser) {
		if (mapToNotify.containsKey(floor)) {
			mapToNotify.get(floor).add(elevatorUser);
		}
		else {
			mapToNotify.put(floor, Arrays.asList(elevatorUser));
		}
	}
	
	private void arriveToANewFloor(Floor floor) {
		if (mapToNotify.containsKey(floor)) {
			List<IElevatorUser> list = mapToNotify.get(floor);
			mapToNotify.remove(list);
			for (IElevatorUser iElevatorUser : list) {
				iElevatorUser.theElevatorIsArrived(this);
			}
		}
	}
	
	public boolean addNewUser(IElevatorUser theUser) {
		if (!isValidUser()) {
			return false;
		}else {
			elevatorUsers.add(theUser);
			return true;
		}
	}
	
	abstract boolean isValidUser();
}
