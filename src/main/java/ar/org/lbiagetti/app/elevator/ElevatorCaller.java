package ar.org.lbiagetti.app.elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorCaller implements ICallerSender {
	List <ICallerObserver> callerObservers ;
	private Step step;
	
	public ElevatorCaller (){
		callerObservers = new ArrayList();
	}
	
	public void addObserver(ICallerObserver theCallerObserver) {
		callerObservers.add(theCallerObserver);
	}
	
	public void goUp() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goUp(step);
		}
	}
	public void goDown() {
		for (ICallerObserver iCallerObserver : callerObservers) {
			iCallerObserver.goDown(step);
		}
	}

}
