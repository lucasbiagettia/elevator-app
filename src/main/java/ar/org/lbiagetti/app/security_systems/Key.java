package ar.org.lbiagetti.app.security_systems;

public class Key {
	private final TypeOfKey permissions;
	protected Key(TypeOfKey thePermissions) {
		permissions = thePermissions;		
	}
	protected TypeOfKey getPermissions() {
		return permissions;
	}
}
