package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.KeyBoard;
import ar.org.lbiagetti.app.initiaization.Logger;

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
	

	public AbstractElevator() {
		mapToNotify = new HashMap<Floor, List<IElevatorUser>>();
		mapToDescend = new HashMap<Floor, List<IElevatorUser>>();
		elevatorUsers = new ArrayList<IElevatorUser>();
		status = ElevatorStatus.STOPPED;
	}

	public void notifyMe(Floor floor, IElevatorUser elevatorUser) {
		if (mapToNotify.containsKey(floor)) {
			mapToNotify.get(floor).add(elevatorUser);
		}
		else {
			mapToNotify.put(floor, Arrays.asList(elevatorUser));
		}
	}
	
	public void toDescend(Floor floor, IElevatorUser elevatorUser) {
		// Este circuito está medio confuso, el array necesito llenarlo acá.
		elevatorUsers.add(elevatorUser);
		if (mapToDescend.containsKey(floor)) {
			mapToDescend.get(floor).add(elevatorUser);
		}
		else {
			mapToDescend.put(floor, Arrays.asList(elevatorUser));
		}	
	}
	
	private void arriveToANewFloor(Floor floor) {
		currentFloor = floor;
		if (mapToNotify.containsKey(floor)) {
			List<IElevatorUser> list = mapToNotify.get(floor);
			mapToNotify.remove(floor);
			for (IElevatorUser iElevatorUser : list) {
				iElevatorUser.theElevatorIsArrived(this);
			}
		}
		if (mapToDescend.containsKey(floor)) {
			List<IElevatorUser> list = mapToDescend.get(floor);
			mapToDescend.remove(floor);
			elevatorUsers.removeAll(list);
			for (IElevatorUser iElevatorUser : list) {
				iElevatorUser.setNewFloor(floor);
			}
		}
	}
	
	// This method must be sincrhonized because two users can try to invoke this
	public synchronized void addNewUser(IElevatorUser theUser) {
		// Sólo tengo que validar esto porque los permisos los valida el elevator manager.
		weightControl(theUser);
	}
	
	private void weightControl(IElevatorUser elevatorUser) {
		// En este caso la consigna sugiere enviar una alarma, to tambien lo stoppeo
		int sum = elevatorUsers.stream().mapToInt(o -> o.getWeight()).sum();
		int maxWeight2 = getMaxWeight();
		if (sum + elevatorUser.getWeight() >= maxWeight2) {
			status = ElevatorStatus.STOPPED;
			sendAlarm();
		}
	}
	
	protected abstract int getMaxWeight();

	private void sendAlarm() {
		keyBoard.sendAlarm(this);
	}

	public KeyBoard getKeyBoard() {
		return keyBoard;
	}
	
	public List<Floor> getQueueForEdit() {
		return queue;
	}
	
	@Override
	public void run() {
		Logger.log("empieza a moverse ", true);
		while (!queue.isEmpty()) {
			Floor floor = queue.get(0);
			queue.remove(0);
			arriveToANewFloor(floor);
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

	public void addKeyBoard(KeyBoard theKeyBoard) {
		keyBoard = theKeyBoard;
	}

	public void setInitialFloor(Floor theInitialFloor) {
		currentFloor = theInitialFloor;
	}

	public void setElevatorStatus(ElevatorStatus elevatorStatus) {
		status = elevatorStatus;
	}
}
