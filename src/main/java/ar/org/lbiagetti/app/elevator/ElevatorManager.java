package ar.org.lbiagetti.app.elevator;

public class ElevatorManager implements ICallerObserver {

	@Override
	public void goUp(Step step) {
		throw new RuntimeException("implementar metodo");		
		
	}

	@Override
	public void goDown(Step step) {
		throw new RuntimeException("implementar metodo");		
	}
	
}
