package uploader;

import java.util.Arrays;

/**
 * Classe responsável por abstrair o pacote de dados a ser enviado.
 * O hash utilizado para realizar o checksum é o MD5.
 * 
 * Gerador: https://www.tablesgenerator.com/text_tables
 * 
 * Tabela representando o modelo do protocolo dos dados.
 * +-----------------------+-----------------+---------------------+
 * | ID [4 bytes]          | piece [4 bytes] | checksum [16 bytes] |
 * +-----------------------+-----------------+---------------------+
 * | Length Name [1 byte]  | Name [Length Name] [0 - 255]          |
 * +-----------------------+---------------------------------------+
 * | Length Data [4 bytes] | Data [Length Data] [0 - 65536]        |
 * +-----------------------+---------------------------------------+
 * 
 * @author daniel
 */
public class PacketData {
	
	private int id;
	private int piece;
	private byte[] checksum = new byte[16]; // MD5
	private String name;
	private byte nameLength;
	private byte[] data = new byte[1024];
	private int dataLength;
	
	public boolean isFinished() {
		return piece == 0;
	}
	
	public PacketData id(int id) {
		this.id = id;
		return this;
	}
	public PacketData piece(int piece) {
		this.piece = piece;
		return this;
	}
	public PacketData name(String name) {
		this.name = name;
		this.nameLength = (byte) name.length();
		return this;
	}
	public PacketData data(byte[] data) {
		System.arraycopy(data, 0, this.data, 0, data.length);
		return this;
	}
	public PacketData dataLength(int length) {
		this.dataLength = length;
		return this;
	}
	
	private PacketData id(byte[] data, int begin) {
		id = Utils.bytesToInt(data, begin);
		return this;
	}
	private PacketData piece(byte[] data, int begin) {
		piece = Utils.bytesToInt(data, begin);
		return this;
	}
	private PacketData checksum(byte[] data, int begin) {
		System.arraycopy(data, begin, this.checksum, 0, 16);
		return this;
	}
	private PacketData name(byte[] data, int begin) {
		nameLength = data[begin++];
		return this.name(new String(data, begin, nameLength));
	}
	private PacketData data(byte[] d, int begin) {
		dataLength = Utils.bytesToInt(d, begin);
		begin += 4;
		System.arraycopy(d, begin, data, 0, dataLength);
		return this;
	}

	public int id() {
		return id;
	}
	public int piece() {
		return piece;
	}
	public String name() {
		return name;
	}
	public byte[] data() {
		return data;
	}
	public int dataLength() {
		return dataLength;
	}

	public int length() {
		return (4 * 3) + 17 + nameLength + dataLength;
	}

	public boolean check(byte[] data) {
		byte[] checksum = Arrays.copyOfRange(data, 8, 24);
		byte[] hash = Utils.hash(this.data, "MD5");
		return Arrays.equals(checksum, hash);
	}

	public byte[] getBytes() {
		byte[] buffer = new byte[this.length()];
		return new PacketCompose(buffer)
				.addInt(id)
				.addInt(piece)
				.saveShift()
				.skip(16)
				.addByte(nameLength)
				.addString(name)
				.addInt(dataLength)
				.addBytes(data, 0, dataLength)
				.restoreShift()
				.addBytes(Utils.hash(data, "MD5"))
				.data();
	}
	public PacketData fromBytes(byte[] bytes) {
		return this
			.id(bytes, 0)						// 4
			.piece(bytes, 4)					// 4
			.checksum(bytes, 8)					// 16
			.name(bytes, 24)					// 1 + nameLength
			.data(bytes, 25 + nameLength);
	}

	public String toString() {
		return "[" + id + "|" + piece + "]" + nameLength + ":" + name + "|"
				+ dataLength + ":" + Utils.stringfy(data, dataLength);
	}
}
