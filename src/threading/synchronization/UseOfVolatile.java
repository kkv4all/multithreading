package threading.synchronization;

import java.util.Scanner;

class Runner extends Thread {

	private volatile static boolean running;

	@Override
	public void run() {
		running = true;
		while(running) {
			System.out.println("Runner " + Thread.currentThread().getId() + " is running");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Runner " + Thread.currentThread().getId() + " is Shutting down");
	}
	
	public void shutdown() {
		running = false;
	}
}

public class UseOfVolatile {
	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.start();
		
		Runner otherRunner = new Runner();
		otherRunner.start();
		
		System.out.println("Press Return key to stop!");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		runner.shutdown();
	}
}