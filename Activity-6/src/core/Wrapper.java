package core;

/**
 * Classe construtura de pacotes. Ele oferece um meio de criar pacotes
 * de uma forma de tratar pacotes de uma forma imutável.
 * 
 * OBS: Pode ser removido em versões futuras, visto seu overhead sobre as
 * operações com o Pacote.
 * 
 * Argumentos e atributos:
 *     mode        (int): Request (1), Response (2)
 *     verb        (int): GET (1), POST (2), PUT (3), DELETE (4), ERROR (5)
 *     format_data (int): AUTO (0), JSON (1), PROTOCOL BUFFER (2)
 *     finished    (int): False (0), True (1)
 *     resource    (str) [max len 255]
 *     params      (str) [max len 255]
 *     data        (bytearray)
 *     
 * @author daniel e mara
 */
public class Wrapper {
	Packet packet = new Packet();
	byte[] bytesNull = new byte[0];
	
	private Packet createPacket(int m, int v, int f, int fi, String r, String p, byte[] d) {
		return packet.mode(m).verb(v).formatData(f).finished(fi).resource(r).params(p).data(d);
	}
	
	public Packet createRequest(int v, int f, int fi, String r, String p, byte[] d) {
		return createPacket(1, v, f, fi, r, p, d);
	}
	
	public Packet createGetRequest(String resource, String params) {
		return createGetRequest(resource, params, 1);
	}
	public Packet createGetRequest(String resource, String params, int format) {
		return createRequest(1, format, 1, resource, params, bytesNull);
	}

	public Packet createPostRequest(String resource, String dataJSON, int format) {
		// "dataJSON" precisa ser enviado no formato de JSON.
		return createRequest(2, 1, format, resource, "", dataJSON.getBytes());
	}
	public Packet createPutRequest(String resource, String params, int format) {
		return createRequest(3, 1, format, resource, params, bytesNull);
	}
	public Packet createDeleteRequest(String resource, String params, int format) {
		return createRequest(4, 1, format, resource, params, bytesNull);
	}
}
