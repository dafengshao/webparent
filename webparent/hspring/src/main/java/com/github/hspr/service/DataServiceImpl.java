package com.github.hspr.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value="dataService")
public class DataServiceImpl implements DataService{
	@Autowired
	private UpdateService updateService;

	@Override
	public String insert(Map<String, String> map) {
		try{
			updateService.update(null, null);
		}catch (Exception e){
			//正常代码
			//save(order);
			//throw new RuntimeException(e);
		}
		return "OK";
	}

}
