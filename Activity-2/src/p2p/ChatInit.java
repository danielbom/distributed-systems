package p2p;

import java.net.UnknownHostException;

/**
 * Aplicação de um chat básico peer to peer.
 * 
 * Está classe é responsável por iniciar a aplicação. O usuário deve digitar o seu
 * nickname para poder se identificar no chate.
 * 
 * @author daniel
 */
public class ChatInit {
	public static void main(String[] args) {
        System.out.print("Informe seu nickname: ");
		String nickname = Utils.getInput();
		String ip = Utils.properties().getProperty("peer.host"); // "127.0.0.1"
		int port = Integer.parseInt(Utils.properties().getProperty("peer.port")); //  

		try {
			new ChatP2P().setServerAddress(ip).setNickname(nickname).run();
		} catch (UnknownHostException e) {
			System.out.println("Não foi possível encontra o servidor " + ip + ".");
		}
	}
}
