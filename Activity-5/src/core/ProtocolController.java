package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Classe responsável por mandar e receber os pacotes do servidor.
 * 
 * TODO: É necessário fornecer um meio de tratar mensagem de tamanho
 * muito grande. Valor 'finished' do pacote sempre está sendo tratado
 * como true (1). Como o pacote é mutável, esta classe pode permanecer
 * desta maneira e classes que a utilizem devem implementar o controle
 * sobre os dados.
 * 
 * @author daniel e mara
 */
public class ProtocolController {

	Wrapper wrapper = new Wrapper();
	
	private OutputStream outputStream;
	private InputStream inputStream;
	
	public ProtocolController(Socket socket) throws IOException {
		if (socket != null) {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		}
	}
	
	private void send(Packet p) throws IOException {
		outputStream.write(p.getBytes());
	}

	private Packet receive() throws IOException {
		byte[] buffer = new byte[4096];
		inputStream.read(buffer);
		return Packet.fromBytes(buffer);
	}

	private Packet doRequest(Packet request) throws IOException {
		send(request);
		Packet p = receive();
		Utils.log(p.toString());
		Utils.log(new String(p.data()));
		return p;
	}

	public Packet requestJSON(int v, String r, String p, byte[] d) throws IOException {
		return doRequest(wrapper.createRequest(v, 1, 1, r, p, d));
	}
	
	public Packet get(String resource, String params, int type) throws IOException {
		return doRequest(wrapper.createGetRequest(resource, params, type));
	}
	public Packet post(String resource, String dataJSON) throws IOException {
		return doRequest(wrapper.createPostRequest(resource, dataJSON, 1));
	}
	public Packet put(String resource, String params) throws IOException {
		return doRequest(wrapper.createPutRequest(resource, params, 1));
	}
	public Packet delete(String resource, String params) throws IOException {
		return doRequest(wrapper.createDeleteRequest(resource, params, 1));
	}

	public void close() {
		try {
			this.inputStream.close();
			this.outputStream.close();
		} catch (IOException e) {
			Utils.log("Erro ao finalizar ProtocolControler");
		}
	}
}
