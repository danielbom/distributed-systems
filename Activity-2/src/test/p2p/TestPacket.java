package test.p2p;

import p2p.Packet;

public class TestPacket {
	public static void main(String[] args) {
		Packet p = new Packet()
				.nickname("Daniel")
				.message("Hello World!");

		System.out.println(p);
		System.out.println();

		Packet q = new Packet().fromBytes(p.getBytes());
		System.out.println(q);
		System.out.println();

		q.message("Hello man");
		System.out.println(q);
		System.out.println();
	}
}
