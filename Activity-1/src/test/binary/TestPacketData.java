package test.binary;

import binary.PacketData;
import binary.Utils;

/**
 * Classe de teste para verificar as operações sobre o pacote de
 * dados.
 * 
 * @author daniel
 */
public class TestPacketData {
	public static void main(String[] args) {
		PacketData p = new PacketData(250)
				.mode(1)
				.operation(4)
				.finished(2)
				.filename("data.txt")
				.data(Utils.intToBytes(8573));
		
		PacketData k = new PacketData(250).fromBytes(p.getBytes());
		System.out.println(p);
		System.out.println(k);
	}
}
