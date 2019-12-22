package threading.concurrency;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	private void increase() {
		for (int i = 0; i < 10000; i++) {
			number++;
		}
	}

	public void firstThread() throws InterruptedException {
		lock.lock();

		System.out.println("Waiting.....");
		condition.await();

		System.out.println("Woken up!");
		try {
			increase();
		} finally {
			lock.unlock();
		}
	}

	public void secondThread() throws InterruptedException {
		Thread.sleep(1000);
		lock.lock();
		
		System.out.println("Press the return key!");
		new Scanner(System.in).nextLine();
		System.out.println("Got the return key!");
		condition.signal();
		
		try {
			increase();
		} finally {
			lock.unlock();
		}
	}

	public void finished() {
		System.out.println("Number: " + number);
	}
}

public class UsingReentrantLock {
	public static void main(String[] args) throws InterruptedException {
		Runner runner = new Runner();
		Thread firstThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		firstThread.start();
		consumerThread.start(); 

		firstThread.join();
		consumerThread.join();
		
		runner.finished();
	}

}