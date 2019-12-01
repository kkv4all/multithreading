package threading.basics;

class MyRunnable implements Runnable {
	@Override
	public void run() {
		 for(int i=0; i <10; i++) {
			 System.out.println("Running Runnable: " + i);
			 
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}
}

public class WithRunnableInterface {
	public static void main(String[] args) {
		Thread runnable = new Thread(new MyRunnable());
		
		runnable.start();
	}
}
