package threading.basics;

class MyThread extends Thread {

	@Override
	public void run() {
		 for(int i=0; i <10; i++) {
			 System.out.println("Running Thread: " + i);
			 
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}
}

public class WithThreadClass {

	public static void main(String[] args) {
		MyThread thread = new MyThread();
		
		thread.start();
	}
}
