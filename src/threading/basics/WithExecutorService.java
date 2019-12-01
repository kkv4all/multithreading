package threading.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RunnableToExecute implements Runnable {
	@Override
	public void run() {
		 for(int i=0; i <10; i++) {
			 System.out.println("Running Executor: " + i);
			 
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}
}

public class WithExecutorService {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(new RunnableToExecute());
		executor.shutdown();
	}
}
