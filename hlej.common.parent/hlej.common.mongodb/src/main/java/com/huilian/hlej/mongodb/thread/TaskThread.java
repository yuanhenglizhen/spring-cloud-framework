package com.huilian.hlej.mongodb.thread;

import com.huilian.hlej.mongodb.service.MongoDBService;
import com.huilian.hlej.mongodb.utils.SpringBeanUtils;

public class TaskThread implements Runnable{
	
	private TaskVO taskVO;
	public TaskThread(TaskVO taskVO) {
		this.taskVO = taskVO;
	}

	@Override
	public void run() {
		MongoDBService mongoDBService = (MongoDBService)SpringBeanUtils.getBean(MongoDBService.class);
		mongoDBService.save(taskVO);
		
	}

}
