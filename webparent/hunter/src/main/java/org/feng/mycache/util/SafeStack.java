package org.feng.mycache.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
/**线程安全栈*/
public class SafeStack<E>{
	
	private final AtomicReference<Element> head = new AtomicReference<Element>(null);
	private int max = 10;

	private final AtomicInteger size = new AtomicInteger(0);
	
	
	public SafeStack(int max){
		this.max = max;
	}
	

	
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
            }
            Element newHead = oldHead;
            //Trying to set the new element as the head
            if(head.compareAndSet(oldHead, newHead)){
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

	
}

/**单向链表*/
final class Element{
	Object data;
	Object next;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getNext() {
		return next;
	}
	public void setNext(Object next) {
		this.next = next;
	}
	
}
