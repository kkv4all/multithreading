package threading.concurrency;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
	private int balance = 10000;

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdraw(int amount) {
		balance -= amount;
	}

	public int getBalance() {
		return balance;
	}

	public static void transfer(Account accountOne, Account accountTwo, int amount) {
		accountOne.withdraw(amount);
		accountTwo.deposit(amount);
	}
}

class MyProcess {
	private Account accountOne = new Account();
	private Account accountTwo = new Account();

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void acquireLock(Lock firstLock,Lock secondLock) throws InterruptedException {
		while(true) {
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;

			try {
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();
			} finally {
				if(gotFirstLock && gotSecondLock) {
					return;
				}

				if(gotFirstLock) {
					firstLock.unlock();
				}
				if(gotSecondLock) {
					secondLock.unlock();
				}
			}
			Thread.sleep(1);
		}
	}

	public void firstThread() throws InterruptedException {
		Random random = new Random();
		for(int i=0; i<10000; i++) {
			//Below Code Creates Deadlock
			//lock1.lock();
			//lock2.lock();
			//Solving Deadlock in method below
			acquireLock(lock1, lock2);
			try {
				Account.transfer(accountOne, accountTwo, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void secondThread() throws InterruptedException {
		Random random = new Random();
		for(int i=0; i<10000; i++) {
			//Below Code Creates Deadlock
			//lock2.lock();
			//lock1.lock();
			//Solving Deadlock in method below
			acquireLock(lock2, lock1);
			try {
				Account.transfer(accountTwo, accountOne, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void finished() {
		System.out.println("Account One Balance: " + accountOne.getBalance());
		System.out.println("Account Two Balance: " + accountTwo.getBalance());
		System.out.println("Total Balance: " + (accountOne.getBalance()+ accountTwo.getBalance()));
	}
}

public class DeadlockSituation {

	public static void main(String[] args) throws InterruptedException {
		MyProcess process = new MyProcess();
		Thread firstThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					process.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread consumerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					process.secondThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		firstThread.start();
		consumerThread.start(); 

		firstThread.join();
		consumerThread.join();

		process.finished();
	}
}
