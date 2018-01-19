package com.huilian.hlej.mongodb.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.huilian.hlej.mongodb.dao.MongoDBDao;


/** 
 *  类            名:   MongoDBDaoImpl
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月21日
 *  @author   WUYI
 *
 */
@Repository
public class MongoDBDaoImpl implements MongoDBDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public void insert(Object object) {
		mongoTemplate.insert(object);
	}

	@Override
	public void save(Object obj) {
		mongoTemplate.save(obj);
	}


	@Override
	public <T> T findOne(Class<T> clazz, Query query) {
		return mongoTemplate.findOne(query, clazz);
	}


	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return mongoTemplate.findAll(clazz);
	}


	@Override
	public <T> T findById(Class<T> clazz, Object id) {
		return mongoTemplate.findById(id, clazz);
	}


	@Override
	public <T> List<T> find(Class<T> clazz, Query query) {
		return mongoTemplate.find(query, clazz);
	}


	@Override
	public <T> List<T> findList(Class<T> clazz, Query query, int currentPage, int pageSize) {
		int startIndex = ((currentPage - 1) < 0 ? 0:(currentPage - 1))*pageSize;
        query.skip(startIndex);
        query.limit(pageSize);
        return mongoTemplate.find(query,clazz);
	}


	@Override
	public <T> long findCount(Class<T> clazz, Query query) {
		return mongoTemplate.count(query, clazz);
	}


	@Override
	public <T> int update(Query query, Update update, Class<T> clazz) {
		return mongoTemplate.updateFirst(query, update, clazz).getN();
	}

}
