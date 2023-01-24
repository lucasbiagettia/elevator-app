package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.KeyBoard;

public abstract class AbstractElevator implements Runnable {
	//TODO inicializar variables en los constructore
	private ElevatorStatus status;
	private Map<Floor, List<IElevatorUser>> mapToNotify;
	private Map<Floor, List<IElevatorUser>> mapToDescend;
	private List <IElevatorUser> elevatorUsers;
	private KeyBoard keyBoard;
	private List<Floor> queue = Collections.synchronizedList(new ArrayList<Floor>());
	private Floor currentFloor;
	private int maxWeight;

	public void notifyMe(Floor floor, IElevatorUser elevatorUser) {
		if (mapToNotify.containsKey(floor)) {
			mapToNotify.get(floor).add(elevatorUser);
		}
		else {
			mapToNotify.put(floor, Arrays.asList(elevatorUser));
		}
	}
	
	public void toDescend(Floor floor, IElevatorUser elevatorUser) {
		elevatorUsers.add(elevatorUser);
		if (mapToDescend.containsKey(floor)) {
			mapToDescend.get(floor).add(elevatorUser);
		}
		else {
			mapToDescend.put(floor, Arrays.asList(elevatorUser));
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
		if (mapToDescend.containsKey(floor)) {
			List<IElevatorUser> list = mapToDescend.get(floor);
			mapToDescend.remove(list);
			elevatorUsers.remove(list);
			for (IElevatorUser iElevatorUser : list) {
				iElevatorUser.setNewFloor(floor);
			}
		}
	}
	
	// This method must be sincrhonized because two users can try to invoke this
	public synchronized boolean addNewUser(IElevatorUser theUser) {
		// TODO
		if (cardControl()) {
			return false;
		}else {
			return true;
		}
	}
	
	private void weightControl(IElevatorUser elevatorUser) {
		int sum = elevatorUsers.stream().mapToInt(o -> o.getWeight()).sum();
		if (sum + elevatorUser.getWeight() >= maxWeight) {
			status = ElevatorStatus.STOPPED;
			sendAlarm();
		}
		
	}
	
	private void sendAlarm() {
		keyBoard.sendAlarm(this);
	}

	abstract boolean cardControl();

	public KeyBoard getKeyBoard() {
		return keyBoard;
	}
	
	public List<Floor> getQueueForEdit() {
		return queue;
	}
	
	@Override
	public void run() {
		while (!queue.isEmpty() && !status.equals(ElevatorStatus.STOPPED)) {
			Floor floor = queue.get(0);
			arriveToANewFloor(floor);
			queue.remove(0);
			if(!queue.isEmpty()) {
				actualizeStatus(floor, queue.get(0));
			}else {
				status = ElevatorStatus.STOPPED;
			}
			try {
				// This sleep simulates waiting
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}

	protected void actualizeStatus(Floor currentFloor, Floor nextFloor) {
		Integer currentNumberOfFloor = currentFloor.getNumberOfFloor();
		Integer nextNumberOfFloor = nextFloor.getNumberOfFloor();
		if (currentNumberOfFloor-nextNumberOfFloor >0 ) {
			status = ElevatorStatus.ASCENDING;
		}else {
			status = ElevatorStatus.DESCENDING;
		}
	}

	public ElevatorStatus getElevatorStatus() {
		return status;
	}

	public Floor getCurrentFloor() {
		return currentFloor;		
	}

}
