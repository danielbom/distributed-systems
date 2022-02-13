package uploader;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Classe utilizada para encapsular operações que não
 * são diretamente relacionadas com outras classes e
 * também controlar o nível de log do sistema.
 * 
 * @author daniel
 */
public class Utils {

    static Scanner reader = new Scanner(System.in);
	public static boolean showLog = true;
	
	public static boolean walkForFilenamesOfPath(String pathname, Consumer<? super Path> action) {
		try (Stream<Path> paths = Files.walk(Paths.get(pathname))) {
		    paths.filter(Files::isRegularFile)
		        .forEach(action);
		    return true;
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		return true;
	}
	
	public static boolean createFile(String filename) {
		try {
			Files.createFile(Paths.get(filename));
			return true;
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		return false;
	}
	
	public static boolean deleteFile(String filename) {
		try {
			Files.delete(Paths.get(filename));
			return true;
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		return false;
	}

	public static byte[] intToBytes(int x, byte[] bytes, int begin) {
		int r, mask = 0x000F;
		for (int i = 0; i < 4; i++) {
			r = x & mask;
			r >>= i * 4;
			bytes[begin + i] = (byte) r;
			mask <<= 4;
		}
		return bytes;
	}
	public static byte[] intToBytes(int x, byte[] bytes) {
		return Utils.intToBytes(x, bytes, 0);
	}
	public static byte[] intToBytes(int x) {
		return Utils.intToBytes(x, new byte[4], 0);
	}
	
	public static int bytesToInt(byte[] bytes, int begin) {
		int x = 0;
		for (int i = 0; i < 4; i++) 
			x += ((int) bytes[i + begin] << (4 * i));
		return x;
	}
	public static int bytesToInt(byte[] bytes) {
		return Utils.bytesToInt(bytes, 0);
	}
	
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

	public static String getInput() {
		String message = "";
		while (message.equals("")) {
			message = reader.nextLine().trim();
		}
		return message;
	}
	
	public static int getInt() {
		String input = "";
		while (!input.matches("\\d+")) {
			input = reader.nextLine().trim();
		}
		return Integer.parseInt(input);
	}
	
	public static byte[] hash(byte[] data, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String hex(byte[] bytes, int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int upper = ((bytes[i] >> 4) & 0xf) << 4;
			int lower = bytes[i] & 0xf;
			if (upper == 0)
				s.append('0');
			s.append(Integer.toHexString(upper | lower));
		}
		return s.toString();
	}
	public static String hex(byte[] bytes) {
		return Utils.hex(bytes, bytes.length);
	}

	public static String stringfy(byte[] bytes, int length) {
		StringBuilder s = new StringBuilder();
		boolean printable;
		for (int i = 0; i < length; i++) {
			printable = Character.isLetterOrDigit(bytes[i]);
			s.append(printable ? (char) bytes[i] : '.');
		}
		return s.toString();
	}
	public static String stringfy(byte[] bytes) {
		return Utils.stringfy(bytes, bytes.length);
	}
}
