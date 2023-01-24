package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.Direction;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerObserver;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerSender;
import ar.org.lbiagetti.app.initiaization.Logger;

public class ElevatorCaller implements ICallerSender {
	List <ICallerObserver> callerObservers ;
	final AbstractElevator elevator;
	final Floor floor;
	
	public ElevatorCaller (AbstractElevator theElevator,Floor theFloor){
		callerObservers = new ArrayList<ICallerObserver>();
		elevator = theElevator;
		floor = theFloor;
	}
	
	public void addObserver(ICallerObserver theCallerObserver) {
		callerObservers.add(theCallerObserver);
	}
	
	//TODO mostrar excepciòn si el piso es el último o el primero
	public void call(Direction direction) {
		for (ICallerObserver iCallerObserver : callerObservers) {
			Logger.log("El elevator caller llama observer" , true);
			iCallerObserver.call(elevator, floor, direction);
		}
	}

	public AbstractElevator getElevator() {
		return elevator;
	}
}
