package threading.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {

	private int id;
	private String name;
	private CountDownLatch latch;

	public Task(int id, String name, CountDownLatch latch) {
		this.id = id;
		this.name = name;
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println(name + " Started....");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(name + " Completed.");
		latch.countDown();
	}
	
}

public class WithCountdownLatch {

	public static void main(String[] args) {
		String[] tasks = {"Caching Service", "Alert Service", "Validation Service"};
		CountDownLatch latch = new CountDownLatch(tasks.length);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0; i<tasks.length; i++) {
			executor.submit(new Task(i,tasks[i],latch));
		}
		executor.shutdown();
		
		try {
			latch.await();
			System.out.println("All Task are Completed.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
