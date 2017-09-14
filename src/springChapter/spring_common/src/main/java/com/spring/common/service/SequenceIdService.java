package com.spring.common.service;


/**
 * 全局id生成器<br/>
 * 1. 单应用内线程同步访问; 2. redis缓存设为同步访问； 3. 缓存没有才去数据库里获取最大的值；
 * 
 * @author chenhaiyan
 * @date 2016年3月30日
 */
public interface SequenceIdService {

	public final static String KEY_PREFIT = "SEQ_ID_";

	public final static String LOCK_PREFIT = "LOCK_";
	

	/**
	 * 不带锁的获取一个ID值(用在单机版本)。不一定安全但更快
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public Long generateId() throws Exception;

	/**
	 * 带锁的获取一个ID值（用在分布式Redis环境中）
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public Long synchGenerateId() throws Exception;

}
