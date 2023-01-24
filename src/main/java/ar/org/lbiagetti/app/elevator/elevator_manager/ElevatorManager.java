package ar.org.lbiagetti.app.elevator.elevator_manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.ElevatorStatus;
import ar.org.lbiagetti.app.elevator.IElevatorUser;
import ar.org.lbiagetti.app.initialization.Logger;
import ar.org.lbiagetti.app.security_systems.KeyManager;

public class ElevatorManager implements ICallerObserver {
	private List<AbstractElevator> elevators;
	private Building building; // TODO ¿necesita saber a qué edificio pertenece?

	public ElevatorManager() {
		elevators = new ArrayList<AbstractElevator>();
	}

	public void addElevator(AbstractElevator theElevator) {
		elevators.add(theElevator);
	}

	public void setBuilding(Building theBuilding) {
		building = theBuilding;
	}
	
	
//  este es el método que usamos para llamar al ascensor desde afuera.	
//	me llaman
//	opciones:
//		1- sin cola --> agrego directamente
//		2- elevador subiendo
//			2 a- quiero subir
//				el elevador está arriba mio --> espero a que TERMINE de bajar y me añado
//				el elevador está abajo mio --> me añado en mi posición
//			2 b- quiero bajar
//				- espero a que termine de subir y me añado en mi posicion
//		3- elevador bajando
//			3 a- quiero subir
//				esperoa que temrine de bajar y me meto en mi posicion
//			3 b- quiero bajar
//				el elevador esta arriba mio --> me añado
//				el elevador esta abajo mio --> espero a que termine de subir y me añado, es decir al final

	@Override
	public void call(AbstractElevator elevator, Floor floor, Direction direction) {
		Logger.log("El caller observer empeiza a trabajar", true);
		ElevatorStatus elevatorStatus = elevator.getElevatorStatus();
		List<Floor> queue = elevator.getQueueForEdit();
		Floor currentFloor = elevator.getCurrentFloor();
		Floor maxFloor = queue.stream().max(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		Floor minFloor = queue.stream().min(Comparator.comparingInt(Floor::getNumberOfFloor)).orElse(null);
		if (queue.isEmpty()) {
			Logger.log("la cola estaba vacia asi que le damos un hilo", true);
			queue.add(floor);
			Thread thread = new Thread(elevator);
			thread.start();
			return;
		}
		if (queue.contains(currentFloor)) {
			return;
		}
		switch (elevatorStatus) {
		case ASCENDING:
			switch (direction) {
			case GO_UP:
				if (currentFloor.getNumberOfFloor() > floor.getNumberOfFloor()) {
					int minIndex = queue.indexOf(minFloor);
					if (queue.size() >= 1) {
						for (int i = minIndex; i < queue.size() - 1; i++) {
							int numOfIFloor = queue.get(i).getNumberOfFloor();
							int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
							if (numOfIFloor < floor.getNumberOfFloor() && floor.getNumberOfFloor() < numOfIPlusFloor) {
								queue.add(i, floor);
							} else if (i == queue.size() - 1) {
								queue.add(currentFloor);
							}
						}
					} else {
						queue.add(floor);
						break;
					}
				} else {
					if (queue.size() >= 1) {
						for (int i = 0; i < queue.size() - 1; i++) {
							int numOfIFloor = queue.get(i).getNumberOfFloor();
							int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
							if (numOfIFloor < floor.getNumberOfFloor() && floor.getNumberOfFloor() < numOfIPlusFloor) {
								queue.add(i, floor);
							} else if (i == queue.size() - 1) {
								queue.add(currentFloor);
							}
						}
					} else {
						queue.add(floor);
						break;
					}
				}
				break;
			case GO_DOWN:
				int maxIndex = queue.indexOf(maxFloor);
				if (queue.size() >= 1) {
					for (int i = maxIndex; i < queue.size() - 1; i++) {
						int numOfIFloor = queue.get(i).getNumberOfFloor();
						int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
						if (numOfIFloor > floor.getNumberOfFloor() && floor.getNumberOfFloor() > numOfIPlusFloor) {
							queue.add(i, floor);
						} else if (i == queue.size() - 1) {
							queue.add(currentFloor);
						}
					}
				} else {
					queue.add(floor);
					break;
				}

				break;
			}
			break;
		case DESCENDING:
			switch (direction) {
			case GO_UP:
				int minIndex = queue.indexOf(minFloor);
				if (queue.size() >= 1) {
					for (int i = minIndex; i < queue.size() - 1; i++) {
						int numOfIFloor = queue.get(i).getNumberOfFloor();
						int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
						if (numOfIFloor < floor.getNumberOfFloor() && floor.getNumberOfFloor() < numOfIPlusFloor) {
							queue.add(i, floor);
						} else if (i == queue.size() - 1) {
							queue.add(currentFloor);
						}
					}
				} else {
					queue.add(floor);
					break;
				}
				break;
			case GO_DOWN:
//				3 b- quiero bajar
//				
//				el elevador esta abajo mio --> espero a que termine de subir y me añado, es decir al final
//				el elevador esta arriba mio --> me añado
				int maxIndex = queue.indexOf(maxFloor);
				if (currentFloor.getNumberOfFloor() < floor.getNumberOfFloor()) {
					if (queue.size() >= 1) {
						for (int i = maxIndex; i < queue.size() - 1; i++) {
							int numOfIFloor = queue.get(i).getNumberOfFloor();
							int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
							if (numOfIFloor > floor.getNumberOfFloor() && floor.getNumberOfFloor() > numOfIPlusFloor) {
								queue.add(i, floor);
							} else if (i == queue.size() - 1) {
								queue.add(currentFloor);
							}
						}
					} else {
						queue.add(floor);
						break;
					}
				} else {
					if (queue.size() >= 1) {
						for (int i = 0; i < queue.size() - 1; i++) {
							int numOfIFloor = queue.get(i).getNumberOfFloor();
							int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
							if (numOfIFloor > floor.getNumberOfFloor() && floor.getNumberOfFloor() > numOfIPlusFloor) {
								queue.add(i, floor);
							} else if (i == queue.size() - 1) {
								queue.add(currentFloor);
							}
						}
					} else {
						queue.add(floor);
						break;
					}
				}
				break;
			}
		default:
			// Si me equivoque en la algoritmia que no se quede afuera
			queue.add(floor);
			break;
		}

	}

	public List<AbstractElevator> getElevators() {
		return elevators;
	}

	// Es más fácil, recorro el for y cuando esté entre dos, cambie el sentido, o
	// llegue al final.
	public void goToFloor(Floor floor, AbstractElevator elevator, IElevatorUser elevatorUser) throws ElevatorException {
		if (!KeyManager.hasPermissions(elevatorUser, floor, elevator)) {
			throw new ElevatorException();
		}
		elevator.toDescend(floor, elevatorUser);
		List<Floor> queue = elevator.getQueueForEdit();
		if (queue.isEmpty()) {
			queue.add(floor);
			Thread thread = new Thread(elevator);
			thread.start();
			return;
		} else {
			ElevatorStatus status = elevator.getElevatorStatus();
			if (queue.size() == 1) {
				if (status.equals(ElevatorStatus.ASCENDING)) {
					if (floor.getNumberOfFloor() > queue.get(0).getNumberOfFloor()) {
						queue.add(floor);
						return;
					} else {
						queue.add(0, floor);
						return;
					}
				} else {
					if (floor.getNumberOfFloor() < queue.get(0).getNumberOfFloor()) {
						queue.add(floor);
						return;
					} else {
						queue.add(0, floor);
						return;
					}
				}
			} else {
				for (int i = 0; i < queue.size() + 1; i++) {
					int numOfIFloor = queue.get(i).getNumberOfFloor();
					int numOfIPlusFloor = queue.get(i + 1).getNumberOfFloor();
					if (numOfIFloor < floor.getNumberOfFloor() && floor.getNumberOfFloor() < numOfIPlusFloor
							|| numOfIFloor > floor.getNumberOfFloor() && floor.getNumberOfFloor() > numOfIPlusFloor) {
						queue.add(i, floor);
						return;
					} else if (i == queue.size() - 1) {
						queue.add(floor);
						return;
					} else if (status.equals(ElevatorStatus.ASCENDING) && numOfIFloor < numOfIPlusFloor 
							||status.equals(ElevatorStatus.DESCENDING) && numOfIFloor > numOfIPlusFloor) {
						queue.add(i,floor);
					}
				}
			}
		}
	}

	public void alarm(AbstractElevator abstractElevator) {
		while (true) {
			System.err.println("Problemas en el elevator " + abstractElevator.toString());
		}
	}

	public void callInmediately(AbstractElevator elevator, Floor floor) {
		List<Floor> queue = elevator.getQueueForEdit();
		queue.add(0, floor);
		if (queue.size() == 1) {
			Thread thread = new Thread(elevator);
			thread.start();
		}
	}

	public Building getBuilding() {
		return building;
	}
}
