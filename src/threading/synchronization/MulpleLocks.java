package threading.synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Worker {
	private Random random = new Random();
	private List<Integer> listOne = new ArrayList<Integer>();
	private List<Integer> listTwo = new ArrayList<Integer>();

	public synchronized void stageOne() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		listOne.add(random.nextInt(100));
	}

	public synchronized void stageTwo() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		listTwo.add(random.nextInt(100));
	}

	public void process() {
		for(int i=0;i<1000;i++) {
			stageOne();
			stageTwo();
		}
	}

	public void main() {
		System.out.println("Starting....");
		long start = System.currentTimeMillis();
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});
		
		thread.start();
		thread2.start();
		
		try {
			thread.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time Taken (in milliseconds): " + (end-start));
		System.out.println("List One Size: " + listOne.size() + " List Two Size: "+ listTwo.size());
	}
}

public class MulpleLocks {

	public static void main(String[] args) {
		Worker worker = new Worker();
		worker.main();
	}
}
