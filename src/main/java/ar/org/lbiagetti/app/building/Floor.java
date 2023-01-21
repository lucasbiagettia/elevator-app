package ar.org.lbiagetti.app.building;

public class Floor {
	final Building building;
	final Integer numberOfFloor;
	final String token;
	
	public Floor (Building theBuiding, Integer theNumberOfFloor, String theToken) {
		building = theBuiding;
		numberOfFloor = theNumberOfFloor;
		token = theToken;
	}

	public Building getBuilding() {
		return building;
	}

	public Integer getNumberOfFloor() {
		return numberOfFloor;
	}

	public String getToken() {
		return token;
	}
	
}
