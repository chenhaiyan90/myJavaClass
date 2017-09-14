package com.spring.common.redis;

import java.util.HashSet;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;

/**
 * Redisson的客户端，用来加锁访问redis
 * 
 * @author chenhaiyan
 * @date 2016年3月31日
 */
public class MyRedissonClient {

	private String hosts;
	private String password;
	private String masterName;
	private String sentinelAddress;
	private int maxActive;
	private int maxIdle;

	/**
	 *  默认使用主从配置
	 * @Description:
	 * @return
	 */
	public RedissonClient getRedissonClient() {
 
		return this.getMasterSlaveClient();
	}

	
	/**
	 * 使用主从配置
	 * @Description:
	 * @return
	 */
	public RedissonClient getMasterSlaveClient(){

		Config config = new Config();
        config.useMasterSlaveServers()   
            .setMasterAddress(hosts).setPassword(password) 
            .setMasterConnectionPoolSize(maxActive)
			.setSlaveConnectionPoolSize((int)(maxActive / 2)); 
        
        RedissonClient redisson = Redisson.create(config);
		return redisson;
	}
	
	/**
	 * 使用哨兵配置
	 * @Description:
	 * @return
	 */
	public RedissonClient getSentinelClient(){

		Config config = new Config();
		
		String [] address=sentinelAddress.split(",");
		
		config.useSentinelServers().setMasterName(this.masterName)
		.addSentinelAddress(address).setPassword(password)
		.setMasterConnectionPoolSize(maxActive)
		.setSlaveConnectionPoolSize((int)(maxActive / 2));
        
        RedissonClient redisson = Redisson.create(config);
		return redisson;
	}
	
	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSentinelAddress() {
		return sentinelAddress;
	}

	public void setSentinelAddress(String sentinelAddress) {
		this.sentinelAddress = sentinelAddress;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
}
