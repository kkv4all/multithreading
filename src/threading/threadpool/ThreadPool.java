package threading.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Process implements Runnable {
	private int id;
	
	public Process (int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Starting : " + id);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed : " + id);
	}
}

public class ThreadPool {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i=0;i<5;i++) {
			executor.submit(new Process(i));
		}
		
		System.out.println("All processes are submitted");
		executor.shutdown();
		
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All processes are completed");
	}
}
