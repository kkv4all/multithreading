package threading.concurrency;

import java.util.Scanner;

class Process {
	void produce() throws InterruptedException {
		synchronized(this) {
			System.out.println("Producer Thread running....");
			wait();
			System.out.println("Resumed.");
		}
	}
	
	void consume() throws InterruptedException {
		Scanner scan = new Scanner(System.in);
		Thread.sleep(2000);
		
		synchronized (this) {
			System.out.println("Waiting for return key.");
			scan.nextLine();
			System.out.println("Return key passed.");
			notify();
		}
	}
}
public class WaitAndNotify {

	public static void main(String[] args) throws InterruptedException {
		Process process = new Process();
		Thread producerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					process.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					process.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		producerThread.start();
		consumerThread.start();
		
		producerThread.join();
		consumerThread.join();
	}

}
