package ar.org.lbiagetti.app.initiaization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticConfiguration {
	static Integer numberOfFloors = 50;
	static Integer numberOfBasements = 1;
	static List <Integer> restrictedFloors = new ArrayList(Arrays.asList(-1,50));
	public static boolean debugLogger = false;
}
