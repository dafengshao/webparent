package com.github.hspr.service;

import org.springframework.stereotype.Service;

@Service(value="updateService")
public class UpdateServiceImpl implements UpdateService{

	@Override
	public int update(String id, String newValue){
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id==null){
			throw new RuntimeException("update time out"); 
		}
		return 0;
	}

}
