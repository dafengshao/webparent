package com.hwf.common.pool;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
/**线程安全栈*/
public class SafeStack<E>{
	
	private final AtomicReference<Element> head = new AtomicReference<Element>(null);
	private int max = 0;

	private final AtomicInteger size = new AtomicInteger(0);
	
	
	

	
	public void push(E e){
		Element newEle = new Element();
		newEle.setData(e);
		while(true){
			/*if(size.intValue()>=max){
				continue;
			}*/
			Element old = head.get();
			newEle.setNext(old);
			if(head.compareAndSet(old, newEle)){
				size.incrementAndGet();
				return ;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public E pop(){
		while(true){
            Element oldHead = head.get();
            //The stack is empty
            if(oldHead == null){
                return null;
            	/*try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	continue;*/
            }
            Element newHead = oldHead.getNext();
            //Trying to set the new element as the head
            if(head.compareAndSet(oldHead, newHead)){
            	size.decrementAndGet();
                return ((E) oldHead.getData());
            }
        }
	}
	
	public E pop(long timeout){
		long start = System.currentTimeMillis();
		while (true) {
			long end = System.currentTimeMillis();
			if(end - start>timeout){
				return null;
			}
			Element oldHead = head.get();
			// The stack is empty
			if (oldHead == null) {
				try {
					Thread.sleep(1);
					continue;
				} catch (InterruptedException e) {
					
				}
			}
			Element newHead = oldHead.getNext();
			// Trying to set the new element as the head
			if (head.compareAndSet(oldHead, newHead)) {
				size.decrementAndGet();
				return ((E) oldHead.getData());
			}
		}
    }

	public int getSize() {
		return size.intValue();
	}

	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}

	public static void main(String[] args) {
		final SafeStack<Integer> stack = new SafeStack<Integer>();
		for(int i=0;i<1;i++){
			stack.push(i);
		}
		for(int i=0;i<101;i++){
			Thread t = new Thread(){
				@Override
				public void run() {
					Integer pop = stack.pop();
					System.out.println(pop);
				}
			};
			t.start();
		}
	}
}

/**单向链表*/
final class Element{
	Object data;
	Element next;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Element getNext() {
		return next;
	}
	public void setNext(Element next) {
		this.next = next;
	}
	
}
