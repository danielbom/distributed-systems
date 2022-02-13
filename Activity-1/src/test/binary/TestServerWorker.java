package test.binary;

import java.io.IOException;

import binary.PacketRequest;
import binary.ServerWorker;
import binary.Wrapper;

public class TestServerWorker {
	public static void main(String[] args) {
		try {
			ServerWorker s = new ServerWorker(null, "/home/daniel/shared/");

			Wrapper w = new Wrapper();
			PacketRequest request = w.packetRequest();

			w.createPacketAdd("testfile1");
			s.rebuildPacket(request.getBytes());
			System.out.println();

			w.createPacketDel("testfile22");
			s.rebuildPacket(request.getBytes());
			System.out.println();

			w.createPacketList();
			s.rebuildPacket(request.getBytes());
			System.out.println();

			w.createPacketGet("testfile333");
			s.rebuildPacket(request.getBytes());
			System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
