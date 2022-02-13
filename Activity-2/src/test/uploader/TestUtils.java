package test.uploader;

import uploader.Utils;

public class TestUtils {
	
	public static void test(String message) {
		byte[] hash = Utils.hash(message.getBytes(), "md5");
		System.out.println("Mensagem: " + message);
		String hex = Utils.hex(hash);
		System.out.println("Hash: " + hex);
		System.out.println("Hash length: " + hash.length);
		System.out.println();
	}
	
	public static void main(String[] args) {
		test("Hello world!");
		test("Hello W+orld!");
		test("Hello  world!");
	}
}
