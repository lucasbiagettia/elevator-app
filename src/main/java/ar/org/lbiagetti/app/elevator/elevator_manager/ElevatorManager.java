package ar.org.lbiagetti.app.elevator.elevator_manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorStatus;
import ar.org.lbiagetti.app.elevator.IElevatorUser;

public class ElevatorManager implements ICallerObserver {
	private List<AbstractElevator> elevators;
	private Building building; // TODO ¿necesita saber a qué edificio pertenece?

	public ElevatorManager() {
		elevators = new ArrayList();
	}

	public void addElevator(AbstractElevator theElevator) {
		elevators.add(theElevator);
	}

	public void setBuilding(Building theBuilding) {
		building = theBuilding;
	}

	// Si el elevador está quieto envío la señal derecho y al final

	// Si el elevador está subiendo y el piso que llama es superior al actual agrego
	// antes de la cota superior o inmediatamente después
	// Si el elevador está subiendo y el piso que llama es inferior al actual agrego
	// en orden después de la cota superior

	// Si el elevador está bajando y el piso que llama es superior al actual agrego
	// en orden después de la cota inferior
	// Si el elevador está bajando y el piso que llama es superior al actual agrego
	// antes de la cota inferior o inmediatamente después
	@Override
	public void call(AbstractElevator elevator, Floor floor, Direction direction, IElevatorUser elevatorUser) {
		ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
		List<Floor> queueForEdit = elevator.getQueueForEdit();
		Floor currentFloor = elevator.getCurrentFloor();
		Floor maxFloor = queueForEdit.stream().max(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		Floor minFloor = queueForEdit.stream().min(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		if (queueForEdit.contains(currentFloor)) {
			return;
		}

		switch (elevatorStatus) {
		case STOPPED:
			queueForEdit.add(floor);
			Thread thread = new Thread(elevator);
			thread.start();
			break;
		case ASCENDING:
			if (queueForEdit.size() == 1) {
				int numberOfUniqueFloor = queueForEdit.get(0).getNumberOfFloor();
				if (numberOfUniqueFloor > floor.getNumberOfFloor()) {
					queueForEdit.add(0, floor);
				} else {
					queueForEdit.add(1, floor);
				}
				break;
			}
			if (currentFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {
				if (maxFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {
					int maxIndex = queueForEdit.indexOf(maxFloor);
					queueForEdit.add(maxIndex + 1, floor);
					break;
				} else {
					for (int i = 0; i < queueForEdit.size() - 1; i++) {
						int numberOfIFloor = queueForEdit.get(i).getNumberOfFloor();
						int numberOfIPlusFloor = queueForEdit.get(i + 1).getNumberOfFloor();
						if (numberOfIFloor < floor.getNumberOfFloor()
								&& numberOfIPlusFloor > floor.getNumberOfFloor()) {
							queueForEdit.add(i, floor);
							break;
						}
					}
				}
			} else {
				int maxIndex = queueForEdit.indexOf(maxFloor);
				if (queueForEdit.size() == maxIndex) {
					queueForEdit.add(floor);
					break;
				} else {
					for (int i = maxIndex; i < queueForEdit.size() - 1; i++) {
						int numberOfIFloor = queueForEdit.get(i).getNumberOfFloor();
						int numberOfIPlusFloor = queueForEdit.get(i + 1).getNumberOfFloor();
						if (numberOfIFloor > floor.getNumberOfFloor()
								&& numberOfIPlusFloor < floor.getNumberOfFloor()) {
							queueForEdit.add(i, floor);
							break;
						}
					}
				}
			}
			break;
		case DESCENDING:
			if (queueForEdit.size() == 1) {
				int numberOfUniqueFloor = queueForEdit.get(0).getNumberOfFloor();
				if (numberOfUniqueFloor < floor.getNumberOfFloor()) {
					queueForEdit.add(0, floor);
					break;
				} else {
					queueForEdit.add(1, floor);
					break;
				}
			}
			if (currentFloor.getNumberOfFloor() > floor.getNumberOfFloor()) {
				if (minFloor.getNumberOfFloor() > floor.getNumberOfFloor()) {
					int minIndex = queueForEdit.indexOf(minFloor);
					queueForEdit.add(minIndex + 1, floor);
					break;
				} else {
					for (int i = 0; i < queueForEdit.size() - 1; i++) {
						int numberOfIFloor = queueForEdit.get(i).getNumberOfFloor();
						int numberOfIPlusFloor = queueForEdit.get(i + 1).getNumberOfFloor();
						if (numberOfIFloor > floor.getNumberOfFloor()
								&& numberOfIPlusFloor < floor.getNumberOfFloor()) {
							queueForEdit.add(i, floor);
							break;
						}
					}
				}
			} else {
				int minIndex = queueForEdit.indexOf(minFloor);
				if (queueForEdit.size() == minIndex) {
					queueForEdit.add(floor);
					break;
				} else {
					for (int i = minIndex; i < queueForEdit.size() - 1; i++) {
						int numberOfIFloor = queueForEdit.get(i).getNumberOfFloor();
						int numberOfIPlusFloor = queueForEdit.get(i + 1).getNumberOfFloor();
						if (numberOfIFloor < floor.getNumberOfFloor()
								&& numberOfIPlusFloor > floor.getNumberOfFloor()) {
							queueForEdit.add(i, floor);
							break;
						}
					}
				}
			}
			break;
		}
	}	
	
	
	public void call2(AbstractElevator elevator, Floor floor, Direction direction, IElevatorUser elevatorUser) {
		ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
		List<Floor> queue = elevator.getQueueForEdit();
		Floor currentFloor = elevator.getCurrentFloor();
		Floor maxFloor = queue.stream().max(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		Floor minFloor = queue.stream().min(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		if (queue.contains(currentFloor)) {
			return;
		}

		switch (elevatorStatus) {
		case STOPPED:
			queue.add(floor);
			Thread thread = new Thread(elevator);
			thread.start();
			break;
		case ASCENDING:
			switch (direction) {
			case GO_UP:
				if (queue.size() == 1) {
					Integer numberOfFloor = queue.get(0).getNumberOfFloor();
					if (numberOfFloor > floor.getNumberOfFloor()) {
						queue.add(0, floor);
						return;
					}else {
						queue.add(floor);
						return;
					}
				}else {
					if (maxFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {
						indexMax = maxF
					}
				}
				
			case GO_DOWN:
			
			}
		case DESCENDING:
			
		}
	}

	public List<AbstractElevator> getElevators() {
		return elevators;
	}

	public void goToFloor(Floor floor, AbstractElevator elevator, IElevatorUser elevatorUser) {
		elevator.toDescend(floor, elevatorUser);
		List<Floor> queue = elevator.getQueueForEdit();
		if (queue.contains(floor)) {
			return;
		}
		ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
		Floor maxFloor = queue.stream().max(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		Floor minFloor = queue.stream().min(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		if (maxFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {
			indexMax
		}
		if (minFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {

		}
	}

	public void alarm(AbstractElevator abstractElevator) {
		while (true) {
			System.err.println("Problemas en el elevator "+ abstractElevator.toString());
		}
		
	}
}
