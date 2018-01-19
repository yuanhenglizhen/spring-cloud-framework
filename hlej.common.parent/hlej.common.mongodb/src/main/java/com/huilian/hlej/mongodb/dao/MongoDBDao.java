package com.huilian.hlej.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/** 
 *  类            名:   MongoDBDao
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月21日
 *  @author   WUYI
 *
 */
public interface MongoDBDao {
	/**
	 * 插入一个Object类型的
	 * @param object
	 */
	void insert(Object object);
	
	/**
	 * 保存一条记录
	 * @param obj
	 */
	void save(Object obj);
    
    /**
     * 查询一条记录
     * @param clazz
     * @param query
     * @return
     */
    <T> T findOne(Class<T> clazz, Query query);
    
    /**
     * 查询所有
     * @param clazz
     * @return
     */
    <T> List<T> findAll(Class<T> clazz);
    
    /**
     * 根据ID查询一个对象
     * @param clazz
     * @param id
     * @return
     */
    <T> T findById(Class<T> clazz,Object id);
    
    /**
     * 根据条件查询集合对象
     * @param clazz
     * @param query
     * @return
     */
    <T> List<T> find(Class<T> clazz, Query query);
    
    /**
     * 根据条件查询分页集合对象
     * @param clazz
     * @param query
     * @param currentPage
     * @param pageSize
     * @return
     */
    <T> List<T> findList(Class<T> clazz,Query query,int currentPage,int pageSize);
    
    /**
     * 根据条件查询总条数
     * @param clazz
     * @param query
     * @return
     */
    <T> long findCount(Class<T> clazz,Query query);
    
    /**
     * 根据条件更新一条记录
     * @param query
     * @param update
     * @param clazz
     * @return
     */
    <T> int update(Query query,Update update,Class<T> clazz);
}
