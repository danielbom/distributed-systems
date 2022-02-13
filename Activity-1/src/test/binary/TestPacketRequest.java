package test.binary;

import java.util.Arrays;

import binary.PacketRequest;

/**
 * Classe de teste para verificar as operações sobre o pacote de
 * requisição.
 * 
 * @author daniel
 */
public class TestPacketRequest {
	public static void main(String[] args) {
		PacketRequest p = new PacketRequest()
				.mode(2)
				.operation(1)
				.addFilename("ABD")
				.addFilename("HELLO WORLD")
				.addFilename("PYTHON");
		System.out.println(p);
		System.out.println(p.length());

		byte[] b = p.getBytes();
		System.out.println(Arrays.toString(b));
		System.out.println(b.length);

		PacketRequest c = new PacketRequest().fromBytes(b);
		System.out.println(c);
		System.out.println(c.length());
		
		p.flush();
		System.out.println(p);
		System.out.println(p.length());
		
		p.fromBytes(c.getBytes());
		System.out.println(p);
		System.out.println(p.length());
	}
}
