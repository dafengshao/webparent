package com.hwf.common.pool;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class  PoolStack<T> {
    private final AtomicReference<Element<T>> head = new AtomicReference<Element<T>>(null);
    
    private  InstanceFactory<T> instanceFactory;

    private AtomicInteger size = new AtomicInteger(0);
    
    private int max = 10;
    
    public PoolStack(){
    	
    }
    
    public PoolStack(InstanceFactory<T> instanceFactory){
    	this(10,instanceFactory);
    }
    
    public PoolStack(int max,InstanceFactory<T> instanceFactory){
    	if(max>0){
    		this.max = max;
    	}
    	this.instanceFactory = instanceFactory;
    	for(int i=0;i<max;i++){
    		this.push(this.instanceFactory.create());
    	}
    }
    /**入栈*/
    public void push(T value){
    	Element<T> newElement = new Element<T>(value);
        while(true){
        	if(size.intValue()<=max){
        		Element<T> oldHead = head.get();
                newElement.next = oldHead;
                //Trying to set the new element as the head
                if(head.compareAndSet(oldHead, newElement)){
                	size.incrementAndGet();
                    return;
                }
        	}else{
        		//skip
        		newElement = null;
        		return;
        	}
        }
    }
    /**出栈 阻塞式*/
    public T pop(){
        while(true){
            Element<T> oldHead = head.get();
            //The stack is empty
            if(oldHead == null){
            	try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
				}
                continue;
            }
            Element<T> newHead = oldHead.next;

            //Trying to set the new element as the head
            if(head.compareAndSet(oldHead, newHead)){
            	size.decrementAndGet();
                return oldHead.t;
            }
        }
    }
    /**出栈,timeout超时时间 单位ms*/
    public T pop(long timeout){
    	long startTime = System.currentTimeMillis();
        while(true){
            Element<T> oldHead = head.get();
            //The stack is empty
            if(oldHead == null){
            	try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
				}
            	long endTime = System.currentTimeMillis();
            	if(endTime - startTime>timeout){
            		return null;
            	}
                continue;
            }
            Element<T> newHead = oldHead.next;

            //Trying to set the new element as the head
            if(head.compareAndSet(oldHead, newHead)){
            	size.decrementAndGet();
                return oldHead.t;
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



	private static final class Element<T> {
        private final T t;
        private Element<T> next;

        private Element(T t) {
            this.t = t;
        }
    }
	
	//test
	public static void main(String[] args) throws InterruptedException {
		final PoolStack<String> stack = new PoolStack<String>(2,new InstanceFactory<String>() {
			@Override
			public String create() {
				return new Random().nextInt(500)+"";
			}
		});
		String a = stack.pop();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stack.push(a);
		
		int i = 0;
		while(i++<50){
			Thread t = new Thread(){
				@Override
				public void run() {
					String a = stack.pop();
					System.out.println("pop a="+a);
					//stack.push(a);
				}
			};
			t.start();
		}
		
		
		
		
	}
}