package binary;

/**
 * Classe modelo de um pacote de resposta.
 * 
 * @author daniel
 */
public class PacketResponse {

	private byte mode; 
	private byte operation;
	private byte result;

	public PacketResponse mode(int mode) {
		this.mode = (byte) mode;
		return this;
	}
	public PacketResponse operation(int op) {
		operation = (byte) op;
		return this;
	}
	public PacketResponse result(int result) {
		this.result = (byte) result;
		return this;
	}

	public int mode() {
		return mode;
	}
	public int operation() {
		return operation;
	}
	public int result() {
		return result;
	}

	public PacketResponse flush() {
		mode = operation = result = (byte) 0;
		return this;
	}
	
	public PacketResponse fromBytes(byte[] bytes) {
		return this.mode(bytes[0])
			.operation(bytes[1])
			.result(bytes[2]);
	}

	public byte[] getBytes() {
		byte[] buffer = { mode, operation, result };
		return buffer;
	}

	public String toString() {
		return "(" + mode + "|" + operation + "|" + result + ")";
	}

}
