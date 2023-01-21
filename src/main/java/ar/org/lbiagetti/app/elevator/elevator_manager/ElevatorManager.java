package ar.org.lbiagetti.app.elevator.elevator_manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import ar.org.lbiagetti.app.building.Building;
import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;

public class ElevatorManager implements ICallerObserver {
	private List <AbstractElevator> elevators;
	private Building building; // TODO ¿necesita saber a qué edificio pertenece?
	// TODO las queue tienen que ser sincrónicas y atómicas, no pueden accederla dos hilos, y hay que testearlo
	private Map <AbstractElevator, ConcurrentLinkedQueue<Floor>> queues;
	
	public ElevatorManager () {
		elevators = new ArrayList();
	}
	
	@Override
	public void goUp(AbstractElevator elevator, Floor step) {
		// Si el elevador está quieto envío la señal derecho y al final
		// Si el elevador está subiendo y el piso que llama es superior al actual agrego antes de la cota superior o inmediatamente después
		// Si el elevador está subiendo y el piso que llama es inferior al actual agrego en orden después de la cota superior
		// Si el elevador está bajando y el piso que llama es superior al actual agrego en orden después de la cota inferior
		// Si el elevador está bajando y el piso que llama es superior al actual agrego antes de la cota inferior o inmediatamente después		
		
		// SEGUEN chatGPT
//		@Override
//		public void goUp(AbstractElevator elevator, Floor step, Queue<Order> order) {
//		    if (elevator.getState() == ElevatorState.STOPPED) {
//		        order.add(new Order(step, Direction.UP));
//		    } else if (elevator.getState() == ElevatorState.GOING_UP) {
//		        if (step.getNumber() > elevator.getCurrentFloor().getNumber()) {
//		            order.add(new Order(step, Direction.UP));
//		        } else {
//		            order.add(new Order(step, Direction.DOWN));
//		        }
//		    } else {
//		        if (step.getNumber() > elevator.getCurrentFloor().getNumber()) {
//		            order.add(new Order(step, Direction.UP));
//		        } else {
//		            order.add(new Order(step, Direction.DOWN));
//		        }
//		    }
//		}
		throw new RuntimeException("implementar metodo");		
	}

	@Override
	public void goDown(AbstractElevator elevator, Floor step) {
		throw new RuntimeException("implementar metodo");		
	}

	public void addElevator(AbstractElevator theElevator) {
		elevators.add(theElevator);
	}

	public void setBuilding(Building theBuilding) {
		building = theBuilding;		
	}

	public List <AbstractElevator> getElevators() {
		return elevators;
	}
	
}
