package threading.concurrency;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
	
	public static void main(String[] args) throws InterruptedException {
		Thread producerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					consumer();
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
	
	private static void producer() throws InterruptedException {
		Random random = new Random();
		
		while(true) {
			Integer value = random.nextInt(100);
			queue.put(value);
			System.out.println("(+) Added Value: " + value + ", Queue size is: " + queue.size());
		}
	}
	
	private static void consumer() throws InterruptedException {
		Random random = new Random();

		while(true) {
			Thread.sleep(50);
			
			if(random.nextInt(10)==0) {
				Integer value = queue.take();
				
				System.out.println("(-) Taken Value: " + value + ", Queue size is: " + queue.size());
			}
		}
	}

}
