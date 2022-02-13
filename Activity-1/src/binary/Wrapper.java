package binary;

/**
 * Classe reponsável por criar os pacotes básicos
 * de requisição e resposta.
 * 
 * @author daniel
 */
public class Wrapper {
	// mode: REQUEST (1) | RESPONSE (2)
	// operation: ADDFILE (1) | DELETE (2) | GETFILELIST (3) | GETFILE (4)
	private PacketResponse packetResponse = new PacketResponse();
	private PacketRequest packetRequest = new PacketRequest();
		
	private void fillPacketList(String pathname) {
		Utils.walkForFilenamesOfPath(pathname, file -> { 
			this.packetRequest.addFilename(file.toString());	
		});
	}
	
	// BASIC RESPONSE
	public PacketResponse packetResponse() {
		return packetResponse;
	}
	
	private void createBasicResponsePacket(int mode, int op, int res) {
		this.packetResponse.flush().mode(mode).operation(op).result(res);
	}
	public void createResponseAdd(int res) {
		this.createBasicResponsePacket(2,  1, res);
	}
	public void createResponseDel(int res) {
		this.createBasicResponsePacket(2,  2, res);
	}
	public void createResponseList(String pathname) {
		this.createBasicResponsePacket(2, 3, 1);
		this.createBasicRequestPacket(2, 3, "");
		this.fillPacketList(pathname);
	}
	public void createResponseGet(int res) {
		this.createBasicResponsePacket(2, 4, res);
	}

	// REQUEST
	public PacketRequest packetRequest() {
		return packetRequest;
	}
	
	private void createBasicRequestPacket(int mode, int op, String filename) {
		this.packetRequest.flush().mode(mode).operation(op).addFilename(filename);
	}
	public void createPacketAdd(String filename) {
		this.createBasicRequestPacket(1, 1, filename);
	}
	public void createPacketDel(String filename) {
		this.createBasicRequestPacket(1, 2, filename);
	}
	public void createPacketList() {
		this.createBasicRequestPacket(1, 3, "");
	}
	public void createPacketGet(String filename) {
		this.createBasicRequestPacket(1, 4, filename);
	}

}
