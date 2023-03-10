package ar.org.lbiagetti.app.security_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.org.lbiagetti.app.building.Floor;
import ar.org.lbiagetti.app.elevator.AbstractElevator;
import ar.org.lbiagetti.app.elevator.FreightElevator;
import ar.org.lbiagetti.app.elevator.IElevatorUser;
import ar.org.lbiagetti.app.initialization.Logger;

public class KeyManager {
	private static Map<IElevatorUser, Key> verifyFalseKey = new HashMap<IElevatorUser, Key>();
	private static List<Floor> forbiddenFloors = new ArrayList<Floor>();

	public static void giveKey(IElevatorUser keyUser) {
		int i = getRandomInt();
		Key key;
		if (i%3 == 0) {
			key = new Key(TypeOfKey.TOTAL_ACCESS);
			Logger.log("Damos la key total access al user " + keyUser.toString(), true);
		}else {
			key = new Key(TypeOfKey.NORMAL);
			Logger.log("Damos la key normal al user " + keyUser.toString(), true);
		}
		keyUser.giveKey(key);
		verifyFalseKey.put(keyUser, key);
		
	}

	private static int getRandomInt() {
		return (int)Math.random();
	}

	public static void setForbiddenFloors(List<Floor> restrictedFloor) {
		getForbiddenFloors().addAll(restrictedFloor);
	}

	public static boolean hasPermissions(IElevatorUser theUser, Floor floor, AbstractElevator elevator) {
		if (getForbiddenFloors().contains(floor) && elevator instanceof FreightElevator) {
			Key keyUser = theUser.getKey();
			Key keyMap = verifyFalseKey.get(theUser);
			if (keyUser != keyMap) {
				return false;
			}
			if (keyUser.getPermissions() == TypeOfKey.TOTAL_ACCESS) {
				return true;
			}else {
				return false;
			}
		} else {
			return true;
		}
	}

	public static List<Floor> getForbiddenFloors() {
		return forbiddenFloors;
	}


}
