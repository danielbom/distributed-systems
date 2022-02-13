package core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Classe utilizada para encapsular operações que não
 * são diretamente relacionadas com outras classes e
 * também controlar o nível de log do sistema.
 * 
 * @author daniel e mara
 */
public class Utils {
	
	private static Scanner reader = new Scanner(System.in);
	public static boolean showLog = true;
	
	public static void log(String message) {
		if (showLog) System.out.println("LOG: " + message);
	}
	
	public static boolean logBoolean(boolean condition, String message) throws IOException {
		if (condition) {
			System.out.println(message);
			throw new IOException("Condição inválida satisfeita!");
		}
		return condition;
	}
	
	
	public static String getAnyInput() {
		return reader.nextLine();
	}
	
	public static String getInput() {
		String input = reader.nextLine();
		while (input.equals(""))
			input = reader.nextLine();
		return input;
	}
	
	public static boolean isNumber(String value) {
		return value.matches("\\d+(.\\d+)?");
	}
	
	public static boolean isInt(String value) {
		return value.matches("\\d+");
	}
	
	public static int getInt() {
		String input = Utils.getInput();
		while (!Utils.isInt(input))
			input = Utils.getInput();
		return Integer.parseInt(input);
	}
	
	public static String decode(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, "UTF-8");
	}
	
}
