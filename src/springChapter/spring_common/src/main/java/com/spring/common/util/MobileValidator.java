package com.spring.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.common.exception.ApplicationException;

/**
 * 校验手机号码 支持 格式如下: +86 13712345678, +86 137 1234 5678, 137 1234 5678,
 * 13712345678
 * @author chenhaiyan
 */
public class MobileValidator {
    private static final Logger logger = LoggerFactory.getLogger(MobileValidator.class);

    public static boolean validate(String mobile) throws ApplicationException {
        logger.info("validating mobile: " + mobile);
        if (StringUtils.isEmpty(mobile)) {
            throw new ApplicationException(-10002);
        }

        if (mobile.length() != 11) {
            throw new ApplicationException(-10014);
        }

        for (int i = 0; i < mobile.length(); ++i) {
            if (!Character.isDigit(mobile.charAt(i))) {
                throw new ApplicationException(-10014);
            }
        }
        logger.info("validating mobile: " + mobile + " return true");
        return true;
    }
}
