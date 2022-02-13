package test.input;

import java.util.concurrent.TimeUnit;

public class ConsoleInputTest {
	public static void main(String[] args) throws InterruptedException {
		if (args.length != 2) {
			System.out.println("Usage: java ConsoleInputTest <number of tries> "
					+ "<timeout in seconds>");
			System.exit(0);
		}

		ConsoleInput con = new ConsoleInput(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]), TimeUnit.SECONDS);

		String input = con.readLine();
		System.out.println("Done. Your input was: " + input);
	}
}
