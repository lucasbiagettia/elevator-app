package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerObserver;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerSender;

public class ElevatorCaller implements ICallerSender {
	List <ICallerObserver> callerObservers ;
	final AbstractElevator elevator;
	final Floor floor;
	
	public ElevatorCaller (AbstractElevator theElevator,Floor theFloor){
		callerObservers = new ArrayList();
		elevator = theElevator;
		floor = theFloor;
	}
	
	public void addObserver(ICallerObserver theCallerObserver) {
		callerObservers.add(theCallerObserver);
	}
	
	public void goUp() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goUp(elevator, floor);
		}
	}
	public void goDown() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goDown(elevator, floor);
		}
	}

	public AbstractElevator getElevator() {
		return elevator;
	}
}
