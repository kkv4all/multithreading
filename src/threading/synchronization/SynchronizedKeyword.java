package threading.synchronization;

class ConcurrentThread extends Thread {

	private static int count;

	private synchronized static void increaseCount() {
		count++;
	}
	
	@Override
	public void run() {
		for(int i=0; i < 10000; i++) {
			increaseCount();
		}
	}
	
	public static int getCount() {
		return count;
	}
}

public class SynchronizedKeyword {
	
	public static void main(String[] args) {
		ConcurrentThread firstThread = new ConcurrentThread();
		ConcurrentThread secondThread = new ConcurrentThread();
		
		firstThread.start();
		secondThread.start();
		
		try {
			firstThread.join();
			secondThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(ConcurrentThread.getCount());
	}
}
