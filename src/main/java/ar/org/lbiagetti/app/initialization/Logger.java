package ar.org.lbiagetti.app.initialization;

public class Logger {
	
	public static void log(String message, boolean forDebug) {
		if (forDebug) {
			if (StaticConfiguration.debugLogger) {
				System.out.println(message);
			}
		} else {
			System.out.println(message);
		}

	}

}
