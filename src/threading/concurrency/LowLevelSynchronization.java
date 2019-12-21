package threading.concurrency;

import java.util.LinkedList;
import java.util.Random;

class Processor {
	
	private LinkedList<Integer> list = new LinkedList<>();
	private final int LIMIT = 10;
	private Object lock = new Object();
	
	void produce() throws InterruptedException {
		Random random = new Random();

		while(true) {
			synchronized(lock) {
				while(list.size() == LIMIT) {
					lock.wait();
				}
				System.out.print("List size is: " + list.size());
				int value = random.nextInt(100);
				list.add(value);
				System.out.println(", Value Added: " + value);
				lock.notify();
			}
			Thread.sleep(random.nextInt(1000));
		}
	}
	
	void consume() throws InterruptedException {
		Random random = new Random();
		while(true) {
			synchronized (lock) {
				while(list.size() == 0) {
					lock.wait();
				}
				System.out.print("List size is: " + list.size());
				int value = list.removeFirst();
				System.out.println(", Value Removed: " + value);
				lock.notify();
			}
			Thread.sleep(random.nextInt(1000));
		}
	}
}

public class LowLevelSynchronization {
	public static void main(String[] args) throws InterruptedException {
		Processor obj = new Processor();
		Thread producerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					obj.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					obj.consume();
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
