package ar.org.lbiagetti.app.elevator;

public class ElevatorFactory {

	public AbstractElevator getPublicElevator() {
		return new PublicElevator();
	}

	public AbstractElevator getFreightElevator() {
		return new FreightElevator();
	}

}
