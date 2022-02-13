package binary;

/**
 * Classe modelo de um pacote de dados.
 * 
 * @author daniel
 */
public class PacketData {

	private byte mode;
	private byte operation;

	private String filename;
	private int filenameLength;

	private byte finished;
 	
	private byte[] buffer;
	private int bufferLength;

	public PacketData(int length) {
		this.buffer = new byte[length];
	}
	
	public PacketData mode(int mode) {
		this.mode = (byte) mode;
		return this;
	}
	public PacketData operation(int op) {
		this.operation = (byte) op;
		return this;
	}
	public PacketData finished(int finished) {
		this.finished = (byte) finished;
		return this;
	}
	public PacketData filename(String filename) {
		filenameLength = filename.length();
		this.filename = filename;
		return this;
	}
	public PacketData data(byte[] data) {
		System.out.println(data.length + " " + buffer.length);
		System.arraycopy(data, 0, buffer, 0, data.length);
		bufferLength = data.length;
		return this;
	}
	
	public PacketData data(byte[] data, int length) {
		if (length <= buffer.length) {
			System.arraycopy(data, 0, buffer, 0, data.length);
			bufferLength = length;
		}
		return this;
	}
	
	public int mode() {
		return mode;
	}
	public int operation() {
		return operation;
	}
	public int finished() {
		return finished;
	}
	public String filename() {
		return filename;
	}
	public byte[] data() {
		return buffer;
	}
	public int dataLength() {
		return bufferLength;
	}

	public PacketData fromBytes(byte[] bytes) {
		this.mode(bytes[0])
			.operation(bytes[1])
			.finished(bytes[2]);
		
		int shift = 3; 	  	 	 // Jump mode, op, finished
		int n = bytes[shift++];  // Jump filenameLength
		
		// Get filename
		byte[] filename = new byte[n];
		System.arraycopy(bytes, shift, filename, 0, n);
		this.filename(new String(filename));
		filenameLength = n;
		
		// Get data
		shift += n; // Jump filename
		int m = Utils.bytesToInt(bytes, shift);
		shift += 4; // Jump bufferLength
		System.arraycopy(bytes, shift, buffer, 0, m);
		bufferLength = m;
		
		return this;
	}
	
	public byte[] getBytes() {
		/*return ByteBuffer.allocate(this.length())
				.put(mode)
				.put(operation)
				.put(finished)
				.put((byte) filenameLength)
				.put(filename.getBytes())
				.putInt(bufferLength).put(buffer)
				.array();
		*/
		byte[] bytes = new byte[this.length()];
		
		bytes[0] = mode;
		bytes[1] = operation;
		bytes[2] = finished;
		bytes[3] = (byte) filenameLength;
		int shift = 4;
		
		// Get filename
		System.arraycopy(filename.getBytes(), 0, bytes, shift, filenameLength);
		shift += filenameLength;
		
		// Get bufferLength
		byte[] number = Utils.intToBytes(bufferLength);
		System.arraycopy(number, 0, bytes, shift, 4);
		shift += 4;
		
		// Get buffer
		System.arraycopy(buffer, 0, bytes, shift, bufferLength);
		
		return bytes;
	}
	
	public int length() {
		return filenameLength + bufferLength + 8;
	}
	
	public String toString() {
		long sum = 0;
		for (int i = 0; i < bufferLength; i++) sum += buffer[i];
		String str = "(" + mode + "|" + operation + "|" + finished + ")"
				+ filenameLength + filename + "(" + bufferLength + "-" + sum + ")";
		return str;
	}

	public void next() {
		finished++;
		if (finished == 0)
			finished = 1;
	}
	
}
