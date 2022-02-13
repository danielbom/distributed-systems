package p2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;


/**
 * Classe utilizada para encapsular operações que não
 * são diretamente relacionadas com outras classes e
 * também controlar o nível de log do sistema.
 * 
 * @author daniel
 */
public class Utils {

	private static Properties props;
	public static boolean showLog = false;
    static Scanner reader = new Scanner(System.in);
	
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

	public static byte[] intToBytes(int x) {
		byte[] bytes = new byte[4];
		int base = 0x000F;
		int r;
		for (int i = 0; i < 4; i++) {
			r = x & base;
			r >>= i * 4;
			bytes[i] = (byte) r;
			base <<= 4;
		}
		return bytes;
	}
	
	public static int bytesToInt(byte[] bytes) {
		return Utils.bytesToInt(bytes, 0);
	}
	public static int bytesToInt(byte[] bytes, int begin) {
		int x = 0;
		for (int i = 0; i < 4; i++) 
			x += ((int) bytes[i + begin] << (4 * i));
		return x;
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

	public static String currentDirectory() {
		return System.getProperty("user.dir");
	}
	
	public static Properties properties() {
		try {
			if (props != null) return props;
			
			Utils.log("Carregando propriedades.");
			String filename = "/home/daniel/chat.properties";
			FileInputStream stream;
			stream = new FileInputStream(filename);
			props = new Properties();
			props.load(stream);
			Utils.log("Propriedades carregadas com sucesso!");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		return props;
	}
}
