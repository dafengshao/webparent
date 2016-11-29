package com.hwf.avx.elsearch;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/***/
public class ElsClient {
	protected Client client = null;
	//private Logger logger = LoggerFactory.getLogger(ElsClient.class);
	
	public ElsClient(String host,int port) throws Exception {
		TransportClient build = TransportClient.builder().build();
		InetSocketTransportAddress inetSocketTransportAddress = new InetSocketTransportAddress(
				InetAddress.getByName(host), port);
		client = build.addTransportAddress(inetSocketTransportAddress);
	}
	
	public Client get() {
		return client;
	}
	
	public void close(){
		client.close();
	}
	
	
	
}
