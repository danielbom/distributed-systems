package p2p;

public class Packet {
	
	private String nickname;
	private String message;

	public byte[] getBytes() {
		int nlen = nickname.length();
		int mlen = message.length();
		byte[] buffer = new byte[nlen + mlen + 2];
		
		int shift = 0;
		
		buffer[shift++] = (byte) nlen;
		System.arraycopy(nickname.getBytes(), 0, buffer, shift, nlen);
		shift += nlen;
		
		buffer[shift++] = (byte) mlen;
		System.arraycopy(message.getBytes(), 0, buffer, shift, mlen);
		
		return buffer;
	}
	public Packet fromBytes(byte[] data) {
		int shift = 0;
		
		int nlen = data[shift++];
		nickname = new String(data, shift, nlen);
		shift += nlen;
		
		int mlen = data[shift++];
		message = new String(data, shift, mlen);
		
		return this;
	}

	public Packet nickname(String nick) {
		nickname = nick;
		return this;
	}
	public Packet message(String msg) {
		message = msg;
		return this;
	}

	public String nickname() {
		return nickname;
	}
	public String message() {
		return message;
	}
	
	public String toString() {
		int nlen = nickname.length();
		int mlen = message.length();
		return nlen + nickname + mlen + message;
	}
}
