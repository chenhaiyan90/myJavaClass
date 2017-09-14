package com.spring.common.util.obj;

import org.apache.commons.lang3.StringUtils;

import com.spring.common.exception.ApplicationException;

/**
 * 表达式计算类
 * 
 * @author chenhaiyan
 */
public class CalculateExpression {
	private static final char PLUS = '+';
	private static final char MINUS = '-';
	private static final char DIVIDE = '/';
	private static final char MULTIPLY = '*';

	private char method;
	private double number;

	public CalculateExpression(String express) throws ApplicationException {
		if(StringUtils.isEmpty(express)) {
			throw new ApplicationException(-1002);
		}
		this.method = express.charAt(0);
		if (this.method != PLUS && this.method != MINUS && this.method != DIVIDE && this.method != MULTIPLY) {
			throw new ApplicationException(-1002, express);
		}
		try {
			this.number = Double.parseDouble(express.substring(1));
		} catch (NumberFormatException e) {
			throw new ApplicationException(-1002, express);
		}
		if (this.method == DIVIDE && this.number == 0) {
			throw new ApplicationException(-1002, express);
		}
	}

	public Integer getChange(Integer value) {
		Integer after = apply(value);
		return after - value;
	}
	
	public Integer apply(Integer value) {
		if (method == PLUS) {
			return (int) (value + this.number);
		} else if (method == MINUS) {
			return (int) (value - this.number);
		} else if (method == DIVIDE) {
			return (int) (value / this.number);
		}
		return (int) (value * this.number);
	}
}