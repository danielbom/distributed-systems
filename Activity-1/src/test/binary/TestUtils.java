package test.binary;

import binary.Utils;

public class TestUtils {
	public static void main(String[] args) {
		
		String path = "/home/daniel/shared/";
		String file = "test";
		
		System.out.println(Utils.createFile(path + file));

		System.out.println(Utils.deleteFile(path + file));

		Utils.walkForFilenamesOfPath(path, System.out::println);
		
	}
}
