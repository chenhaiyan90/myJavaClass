package com.spring.common.web.vo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.spring.common.exception.ApplicationException;
import com.spring.common.service.RedisService;

/**
 * @author chenhaiyan
 *
 */
@Component
public class RequestVoBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(RequestVo.class);
	private static final Integer TID_LIVE_ITME = 5;

	@Autowired
	@Qualifier("redisService")
	private RedisService<String, String> redisService;
	
	/**
	 * 
	 * @author chenhaiyan
	 * @param appId
	 * @param body
	 * @param type
	 * @return
	 * @throws ApplicationException
	 */
	public <T> RequestVo<T> build(String appId, String body, Class<T> type) throws ApplicationException {
		RequestVo<T> req = JSON.parseObject(body, new TypeReference<RequestVo<T>>(type) {
		});
		if (req == null) {
			throw new ApplicationException(-1000);
		}
		if (req.getParams() == null) {
			throw new ApplicationException(-1000);
		}
		if (StringUtils.isEmpty(appId)) {
			appId = req.getAppId();
		}
		if (StringUtils.isNotEmpty(req.getTransactionId())) {
			String tid = appId + req.getTransactionId();
			String cacheId = redisService.get(tid);
			if (cacheId != null && StringUtils.isNotEmpty(cacheId)) {
				// 5分钟内不能重复对同样transaction进行提交
				throw new ApplicationException(-1001);
			}
			redisService.put(tid, tid, TID_LIVE_ITME);
		}
		return req;
	}

	/**
	 * 
	 * @param appId 如果为空，那么从body里面获取appId
	 * @param body
	 * @return
	 * @throws ApplicationException
	 */
	public <T> RequestVo<T> build(String appId, String body)
			throws ApplicationException {
		//logger.info("request paramters : " + body); 
		RequestVo<T> req = JSON.parseObject(body,
				new TypeReference<RequestVo<T>>() {
				});
		if (req == null) {
			throw new ApplicationException(-1000);
		}
		if (req.getParams() == null) {
			throw new ApplicationException(-1000);
		}
		if (StringUtils.isEmpty(appId)) {
			appId = req.getAppId();
		}
		if (StringUtils.isNotEmpty(req.getTransactionId())) {
			String tid = appId + req.getTransactionId();
			String cacheId = redisService.get(tid);
			if (cacheId != null && StringUtils.isNotEmpty(cacheId)) {// 5分钟内不能重复对同样transaction进行提交
				throw new ApplicationException(-1001);
			}
			redisService.put(tid, tid, TID_LIVE_ITME);
		}
		return req;
	}
	
	/**
	 * 
	 * @Description:
	 * @param appId
	 * @param req
	 * @throws ApplicationException
	 */
	public  void requestValid(String appId,RequestVo<?>  req)throws ApplicationException{
		if (req == null) {
			throw new ApplicationException(-1000);
		}
		if (req.getParams() == null) {
			throw new ApplicationException(-1000);
		}
		if (StringUtils.isEmpty(appId)) {
			appId = req.getAppId();
		}
		if (StringUtils.isNotEmpty(req.getTransactionId())) {
			String tid = appId + req.getTransactionId();
			String cacheId = redisService.get(tid);
			if (cacheId != null && StringUtils.isNotEmpty(cacheId)) {// 5分钟内不能重复对同样transaction进行提交
				throw new ApplicationException(-1001);
			}
			redisService.put(tid, tid, TID_LIVE_ITME);
		}
	}

}
