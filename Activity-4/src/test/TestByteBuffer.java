package test;

import java.nio.ByteBuffer;

/**
 * Teste básico sobre as operações da classe ByteBuffer.
 * 
 * @author daniel e mara
 */
public class TestByteBuffer {
	public static void main(String[] args) {
		ByteBuffer b = ByteBuffer.allocate(200)
				.put((byte) 10)
				.put((byte) 20)
				.put("Hello".getBytes())
				.putShort((short) 10000);
		
		b.asReadOnlyBuffer();
		b.rewind();
		System.out.println(b);
		System.out.println(b.position());
		System.out.println((int) b.get());
		System.out.println(b.position());
		System.out.println((int) b.get());
		System.out.println(b.position());
		byte[] buffer = new byte[5];
		b.get(buffer);
		System.out.println(b.position());
		System.out.println(new String(buffer));
		System.out.println(b.position());
		System.out.println((int) b.getShort());
		System.out.println(b.position());
	}
}
