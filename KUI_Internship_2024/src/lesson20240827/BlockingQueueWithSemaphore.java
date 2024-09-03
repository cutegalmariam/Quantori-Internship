package lesson20240827;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueueWithSemaphore<T> {

	List<T> items = new ArrayList<>();

	Object mutex = new Object();
	
	public void put(T item) {
		items.add(item);
		synchronized (mutex) {
			mutex.notify();
		}
	}

	
	public T get() {
		while (items.isEmpty()) {
			synchronized (mutex) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return items.remove(0);
	}
}
