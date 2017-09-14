package com.spring.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.common.exception.ApplicationException;

/**
 * @author chenhaiyan
 *
 */
public class IdValidator {
	private static final Logger logger = LoggerFactory.getLogger(MobileValidator.class);

	public static boolean validate(String id) throws ApplicationException {
		logger.info("validating id: " + id);
		if (StringUtils.isEmpty(id)) {
			throw new ApplicationException(-10015);
		}

		// 前17位为数字
		for (int i = 0; i < id.length() - 1; ++i) {
			if (!Character.isDigit(id.charAt(i))) {
				throw new ApplicationException(-10015);
			}
		}

		// 校验规则校验
		int sum = 0;
		for (int i = 0; i < 17; ++i) {
			sum += ((Math.pow(2, 17 - i)) % 11) * (id.charAt(i) - '0');
		}
		int check = (12 - (sum % 11)) % 11;
		if (check == 10) {
			if (id.charAt(17) != 'x' && id.charAt(17) != 'X') {
				throw new ApplicationException(-10015);
			}
		} else if (check != (id.charAt(17) - '0')) {
			throw new ApplicationException(-10015);
		}
		logger.info("validating certid: " + id + " return true");
		return true;
	}
}
