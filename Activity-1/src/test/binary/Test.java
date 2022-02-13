package test.binary;

import java.util.Arrays;

import binary.Utils;

/**
 * Classe auxiliar de teste para desenvolver o método
 * de conversão de inteiro para array de bytes.
 * 
 * @author daniel
 */
public class Test {
	
	public static void main(String[] args) {
		int x = 0x000000F;
		int t = 32560;
		// 0111		7
		// 1111		15
		// 0011		3
		// 0000		0

		int r;
		for (int i = 0; i < 4; i++) {
			System.out.println(x);
			r = t & x;
			System.out.println(r);
			r >>= i * 4;
			System.out.println(r);
			x <<= 4;
			System.out.println();
		}

		System.out.println(Arrays.toString(Utils.intToBytes(t)));
		System.out.println(Utils.bytesToInt(Utils.intToBytes(t)));
	}
}
