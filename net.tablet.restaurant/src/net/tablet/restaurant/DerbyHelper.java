package net.tablet.restaurant;

public class DerbyHelper {
	public static String tableAlreadyExists(Exception e) {
		return e.getMessage();
	}
}
