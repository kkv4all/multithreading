package threading.concurrency;

import java.util.Random;

public class InterruptingThread {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting...");
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Random rnd = new Random();
				
				for(int i=0; i < 1E8; i++) {
					/*if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted...");
						break;
					}*/
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						System.out.println("Interrupted...");
						break;
					}
					Math.sin(rnd.nextDouble());
				}
			}
		});
		
		thread.start();
		Thread.sleep(500);
		thread.interrupt();
		thread.join();
		
		System.out.println("Finished...");
	}

}
