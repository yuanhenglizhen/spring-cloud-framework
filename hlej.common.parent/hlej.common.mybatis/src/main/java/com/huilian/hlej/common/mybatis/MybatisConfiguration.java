package com.huilian.hlej.common.mybatis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean; 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;  

/** 
 * sqlSessionFactory
 *  类            名:   MybatisConfiguration
 *  修 改 记 录:   //修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:   版权所有(C)2017-2017
 *  公             司:  汇联金融服务控股有限公司
 *  @version  V1.0
 *  @date     2017年11月23日
 *  @author   cuiyi
 *
 */
@Configuration  
@ConditionalOnClass({EnableTransactionManagement.class})  
@Import({ DataBaseConfiguration.class})  
@PropertySource("classpath:mybatis.properties") 
public class MybatisConfiguration {  
    @Value("${spring.datasource.type}")  
    private Class<? extends DataSource> dataSourceType;  
  
    @Value("${datasource.readSize}")  
    private String dataSourceSize;  
    @Resource(name = "writeDataSource")  
    private DataSource dataSource;  
    @Resource(name = "readDataSources")  
    private List<DataSource> readDataSources;  
  
    @Bean  
    @ConditionalOnMissingBean  
    public SqlSessionFactory sqlSessionFactory() throws Exception {  
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
//        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());  
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.huilian.hlej");  
//        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);  
//        return sqlSessionFactoryBean.getObject();  
        
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());  
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);  
        return sqlSessionFactoryBean.getObject();  
        
    }  
    /** 
     * 有多少个数据源就要配置多少个bean 
     * @return 
     */  
    @Bean  
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {  
        int size = Integer.parseInt(dataSourceSize);  
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);  
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();  
        // DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");  
        // 写  
        
        System.out.println(dataSource.hashCode());
        targetDataSources.put(DataSourceType.write.getType(),dataSource);  
        // targetDataSources.put(DataSourceType.read.getType(),readDataSource);  
        //多个读数据库时  
        for (int i = 0; i < size; i++) {  
            targetDataSources.put(i, readDataSources.get(i));  
        }  
        proxy.setDefaultTargetDataSource(dataSource);  
        proxy.setTargetDataSources(targetDataSources);  
        return proxy;  
    }  
}  
