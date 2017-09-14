package com.spring.common.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * Token 服务
 * @author chenhaiyan
 * @date 2016-3-25 10:00
 */
@Service("randomStringService")
public class RandomStringService {

    public String generate(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }

    public String randomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
}