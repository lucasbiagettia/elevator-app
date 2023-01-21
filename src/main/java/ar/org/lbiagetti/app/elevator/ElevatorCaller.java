package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.List;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerObserver;
import ar.org.lbiagetti.app.elevator.elevator_manager.ICallerSender;

public class ElevatorCaller implements ICallerSender {
	List <ICallerObserver> callerObservers ;
	final Floor floor;
	
	public ElevatorCaller (Floor theFloor){
		callerObservers = new ArrayList();
		floor = theFloor;
	}
	
	public void addObserver(ICallerObserver theCallerObserver) {
		callerObservers.add(theCallerObserver);
	}
	
	public void goUp() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goUp(floor);
		}
	}
	public void goDown() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goDown(floor);
		}
	}

}
