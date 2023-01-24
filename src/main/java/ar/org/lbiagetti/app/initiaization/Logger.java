package ar.org.lbiagetti.app.initiaization;

public class Logger {
	public void log(String message, boolean forDebug) {
		if (forDebug) {
			if (StaticConfiguration.debugLogger) {
				System.out.println(message);
			}
		} else {
			System.out.println(message);
		}

	}

}
