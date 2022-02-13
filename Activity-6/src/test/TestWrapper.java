package test;

import core.Packet;
import core.Wrapper;

/**
 * Teste sobre o pacote (Packet) e o empacotador (Wrapper).
 * 
 * @author daniel e mara
 */
public class TestWrapper {
	
	public static void print(Packet p) {
		System.out.println(Packet.fromBytes(p.getBytes()));
	}
	
	public static void main(String[] args) {
		Wrapper w = new Wrapper();
		print(w.createGetRequest("curso", "select>nome", 2));
		print(w.createPostRequest("curso", "{\"codigo\": 10, \"curso\": \"Curso test\"}", 1));
		print(w.createPutRequest("curso", "query>codigo=10&values>nome=\"Curso mudado\"", 1));
		print(w.createDeleteRequest("curso", "query>codigo=10", 1));
	}
}
