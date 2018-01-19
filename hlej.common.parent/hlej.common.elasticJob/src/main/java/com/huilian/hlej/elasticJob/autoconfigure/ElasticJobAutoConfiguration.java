package com.huilian.hlej.elasticJob.autoconfigure;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.huilian.hlej.elasticJob.annotation.ElasticSimpleJob;


@ConditionalOnExpression("'${elaticjob.zookeeper.server-lists}'.length() > 0")
@Configuration
public class ElasticJobAutoConfiguration {
	@Value("${elaticjob.zookeeper.server-lists}")
	private String serverList;
	
	@Value("${elaticjob.zookeeper.namespace}")
	private String namespace;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private JobEventConfiguration jobEventConfiguration;
	
	
	@PostConstruct
	public void initElasticJob(){
		ZookeeperRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
		regCenter.init();
		Map<String, SimpleJob> map = applicationContext.getBeansOfType(SimpleJob.class);
		for(Map.Entry<String, SimpleJob> entry : map.entrySet()){
			SimpleJob simpleJob = entry.getValue();
			ElasticSimpleJob elasticSimpleJobAnnotation=simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);
			
			String cron=StringUtils.defaultIfBlank(elasticSimpleJobAnnotation.cron(), elasticSimpleJobAnnotation.value());
			SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(simpleJob.getClass().getName(), cron, elasticSimpleJobAnnotation.shardingTotalCount()).shardingItemParameters(elasticSimpleJobAnnotation.shardingItemParameters()).build(), simpleJob.getClass().getCanonicalName());
			LiteJobConfiguration liteJobConfiguration=LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
			
			SpringJobScheduler jobScheduler=new SpringJobScheduler(simpleJob, regCenter, liteJobConfiguration, jobEventConfiguration);
			jobScheduler.init();
	
		}
	}
}
