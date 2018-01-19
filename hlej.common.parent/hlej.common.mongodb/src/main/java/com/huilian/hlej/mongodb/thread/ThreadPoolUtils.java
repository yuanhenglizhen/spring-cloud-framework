package com.huilian.hlej.mongodb.thread;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolUtils {
	
	//启用线程池
	private static ExecutorService fixedThreadPool = null;
	
	
	static {
		fixedThreadPool = Executors.newFixedThreadPool(10);
	}
	
	private static ThreadPoolUtils instance;
	
	/**
	 * 获取实例
	 * @return
	 */
	public static ThreadPoolUtils getInstance(){
		if(instance == null){
			instance = new ThreadPoolUtils();
		}
		return instance;
	}
	//提交數據
	public void submit(TaskVO taskVO) {
		try {
			taskVO.setCreateTime(new Date());
			fixedThreadPool.submit(new TaskThread(taskVO));
		}catch (Exception e) {
			log.error("ThreadPoolUtils@submit--------error----.taskVO.getBizId()----"+taskVO.getBizId(), e);
		}
	}
	
}
