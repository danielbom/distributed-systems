package test.binary;

import java.io.IOException;

import binary.ClientWorker;

/**
 * Classe de teste das operações de criação de pacote.
 * 
 * @author daniel
 */
public class TestClientWorker {
	public static void main(String[] args) throws IOException {
		ClientWorker c = new ClientWorker(null, "path");
		
		c.createPacket("ADDFILE testfile1");
		System.out.println();

		c.createPacket("DELETE testfile22");
		System.out.println();

		c.createPacket("GETFILELIST");
		System.out.println();

		c.createPacket("GETFILE testfile333");
		System.out.println();

		// O pacote mantem lixo.
		c.createPacket("NULL");
		System.out.println();
	}
}
