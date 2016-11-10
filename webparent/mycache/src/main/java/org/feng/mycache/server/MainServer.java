package org.feng.mycache.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.feng.mycache.util.SafeStack;
import org.feng.mycache.util.ServerSocketProess;

public class MainServer {
	private static volatile boolean shutDown = false;
	private static ExecutorService exeAcceptPool = Executors.newFixedThreadPool(50);
	private static ExecutorService exePool = Executors.newFixedThreadPool(500);
	private static SafeStack<ServerSocketProess> safeStack = new SafeStack<ServerSocketProess>(
			500);

	static ServerSocket mainServerSocket = null;

	static Semaphore semaphore = new Semaphore(1000);
	
	public static void main(String[] args) throws Exception {
		MainServer main = new MainServer();
		mainServerSocket = new ServerSocket(6397);
		int a = 0;
		while(a++<500){
			exeAcceptPool.execute(main.new AcceptRunnable());
		}

	}

	public static boolean isShutDown() {
		return shutDown;
	}

	public static void setShutDown(boolean shutDown) {
		MainServer.shutDown = shutDown;
	}

	class AcceptRunnable implements Runnable{
		@Override
		public void run() {
			try {
				while (!shutDown) {
					System.out.println(Thread.currentThread().getName()+"accept...");
					Socket mainSocket = mainServerSocket.accept();
					System.out.println(Thread.currentThread().getName()+"accept...mainSocket...");
					ServerSocketProess pop = safeStack.pop();
					if(pop==null){
						pop = new ServerSocketProess();
					}
					pop.setSocket(mainSocket);
					//pop.call();
					Future<Object> submit = exePool.submit(pop);
					submit.get();
					safeStack.push(pop);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
	}
}
