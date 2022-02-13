package test.uploader;

import uploader.PacketData;

public class TestPacketData {
	public static void main(String[] args) {
		byte[] message = "Hello world!".getBytes();
		PacketData p = new PacketData()
				.id(1)
				.piece(999)
				.name("TEST")
				.data(message)
				.dataLength(message.length);
		
		System.out.println(p);
		System.out.println(new String(p.data()));
		System.out.println();
		
		byte[] data = p.getBytes();

		PacketData k = new PacketData().fromBytes(data);
		System.out.println(k);
		System.out.println(new String(k.data()));
		System.out.println();
		
		System.out.println(k.check(data));
	}
}
