package test.binary;

import binary.Wrapper;

/**
 * Classe de teste da classe Wrapper.
 * 
 * @author daniel
 */
public class TestRequestWrapper {
	public static void main(String[] args) {
		Wrapper w = new Wrapper();
		w.createPacketAdd("AddTest");
		System.out.println(w.packetRequest());
		System.out.println();
		
		w.createPacketDel("DelTest");
		System.out.println(w.packetRequest());
		System.out.println();
		
		w.createPacketGet("GetTest");
		System.out.println(w.packetRequest());
		System.out.println();
		
		w.createPacketList();
		System.out.println(w.packetRequest());
		System.out.println();
	}
}
