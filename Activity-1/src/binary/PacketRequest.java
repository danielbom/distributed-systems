package binary;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe modelo de um pacote de requisição.
 * 
 * @author daniel
 */
public class PacketRequest {

	byte mode; 
	byte operation;

	List<String> filenames = new ArrayList<String>();
	private int lengthOfFilenames;
	private int numberOfFilenames;

	private int maxPacketLength = 0;

	public void setMaxPacketLength(int maxPacketLength) {
		this.flush();
		this.maxPacketLength = maxPacketLength;
	}

	public PacketRequest() {}

	public byte mode() {
		return this.mode;
	}

	public byte operation() {
		return this.operation;
	}

	public PacketRequest mode(int mode) {
		this.mode = (byte) mode;
		return this;
	}

	public PacketRequest operation(int op) {
		this.operation = (byte) op;
		return this;
	}

	public PacketRequest fromBytes(byte[] bytes) {
		this.flush().mode(bytes[0]).operation(bytes[1]);
		for (int i = 2; i < bytes.length; ) {
			byte n = bytes[i++];
			if (n > 0) {
				byte[] buffer = new byte[n];
				System.arraycopy(bytes, i, buffer, 0, n);
				this.addFilename(new String(buffer));
			}
			i += n;
		}
		return this;
	}

	public PacketRequest flush() {
		this.mode = this.operation = (byte) 0;
		this.lengthOfFilenames = 0;
		this.numberOfFilenames = 0;
		this.filenames.clear();
		return this;
	}

	public boolean isPropper(String data) {
		if (this.maxPacketLength == 0) return true;
		
		int length = data.length();
		return length <= 255 && this.length() + length < this.maxPacketLength;
	}

	private void checkLength(String data) {
		int length = data.length();
		if (length > 255) {
			String message = "O tamanho do dado indexado é de no máximo 255 caracteres.";
			throw new IllegalArgumentException(message);
		}
		if (this.maxPacketLength != 0) {
			if (this.length() + length > this.maxPacketLength) {
				throw new RuntimeException(String
					.format("O tamanho máximo (%d) do pacote foi excedido.",
						this.maxPacketLength));
			}
		}
	}

	public PacketRequest addFilename(String data) {
		if (data.length() == 0) return this;
		this.checkLength(data);
		filenames.add(data);
		lengthOfFilenames += data.length();
		numberOfFilenames++;
		return this;
	}

	public int length() {
		return lengthOfFilenames + numberOfFilenames + 2;
	}

	public byte[] getBytes() {
		ByteBuffer b = ByteBuffer.allocate(this.length());
		b.put(mode);
		b.put(operation);
		
		for (String name : this.filenames) {
			b.put((byte) name.length());
			b.put(name.getBytes());
		}
		return b.array();
	}

	public String toString() {
		String str = "(" + mode + "|" + operation + ")";
		for (String name: filenames)
			str += name.length() + name;
		return str;
	}

	public String getFirst() {
		return filenames.size() > 0 ? filenames.get(0) : "";
	}

	public List<String> filenames() {
		return filenames;
	}

}
