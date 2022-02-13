package chat;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Classe utilizada para encapsular operações que não
 * são diretamente relacionadas com outras classes e
 * também controlar o nível de log do sistema.
 * 
 * @author daniel e mara
 */
public class Utils {
	public static boolean showLog = true;

	public static void log(String message) {
		if (Utils.showLog)
			System.out.println("LOG: " + message);
	}

	public static boolean available(int port) {
		// link: https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}
