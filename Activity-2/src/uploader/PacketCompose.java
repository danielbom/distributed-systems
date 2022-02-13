package uploader;

import java.util.Stack;

/**
 * 
 * @author daniel
 * Veja: https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
 */
public class PacketCompose {
	
	private Stack<Integer> saveShifts = new Stack<Integer>();
	
	private byte[] data;
	private int shift;
	
	public PacketCompose(byte[] data) {
		this.data = data;
		shift = 0;
	}
	
	public byte[] data() {
		return data;
	}
	public int shift() {
		return shift;
	}

	public PacketCompose addInt(int x) {
		Utils.intToBytes(x, data, shift);
		shift += 4;
		return this;
	}
	public PacketCompose addString(String str) {
		return this.addBytes(str.getBytes(), 0);
	}
	public PacketCompose addByte(byte b) {
		data[shift++] = b;
		return this;
	}
	public PacketCompose addBytes(byte[] bytes) {
		return this.addBytes(bytes, 0);
	}
	public PacketCompose addBytes(byte[] bytes, int begin) {
		return this.addBytes(bytes, begin, bytes.length);
	}
	public PacketCompose addBytes(byte[] bytes, int begin, int length) {
		System.arraycopy(bytes, begin, data, shift, length);
		shift += length;
		return this;
	}
	
	public PacketCompose skip(int n) {
		shift += n;
		return this;
	}

	public PacketCompose saveShift() {
		this.saveShifts.add(shift);
		return this;
	}
	public PacketCompose restoreShift() {
		this.shift = this.saveShifts.pop();
		return this;
	}
	
}
