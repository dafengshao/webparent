package org.feng.mycache.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerSocketProess implements Callable<Object>{
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	public ServerSocketProess() {
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	private void close() {
		try {
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Object call() throws Exception {
		try{
			System.out.println(Thread.currentThread().getName()+".ServerSocketProess.call...");
			inputStream=socket.getInputStream();
			outputStream=socket.getOutputStream();
			int  read = inputStream.read();
			outputStream.write("b".getBytes());
			outputStream.flush();
			System.out.println("receive mes:"+(char)read);
		}catch(Exception e){
			e.printStackTrace(); 
		}finally{
			
			close();
		}
		return null;
	}
	
}
