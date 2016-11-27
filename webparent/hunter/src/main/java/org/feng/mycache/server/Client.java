package org.feng.mycache.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;	
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static ExecutorService exePool = Executors.newFixedThreadPool(1000);
	public static void main(String[] args) throws IOException {
		int i= 0;
		while(i++<50000){
			exePool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						sentMessage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}

	private static void sentMessage() throws IOException {
		Socket socket = new Socket();
		// ->@wjw_add
		socket.setReuseAddress(true);
		socket.setKeepAlive(true); // Will monitor the TCP connection is
		// valid
		socket.setTcpNoDelay(true); // Socket buffer Whetherclosed, to
		// ensure timely delivery of data
		socket.setSoLinger(true, 0); // Control calls close () method,
		socket.connect(new InetSocketAddress("127.0.0.1",6397));
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		outputStream.write("a".getBytes());
		outputStream.flush();
		//outputStream.close();
		int read = inputStream.read();
		System.out.println((char)read);
	}
}
