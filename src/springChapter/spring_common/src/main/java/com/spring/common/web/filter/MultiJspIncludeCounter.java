package com.spring.common.web.filter;
/**
 * 为了应付 一个页面jsp:include几个页面，引起的数据库连接已经关闭的问题,加入此计数器
 * @author chenhaiyan
 * @date 2013年9月25日
 */
public class MultiJspIncludeCounter {

	private static final ThreadLocal<Integer> actionThreadLocal = new ThreadLocal<Integer>();

	public static void plus() {
		Integer count = getCount();
		if (count == null) {
			actionThreadLocal.set(new Integer(1));
		} else {
			count++;
			actionThreadLocal.set(count);
		}
	}

	public static void unset() {
		actionThreadLocal.remove();
	}

	public static Integer getCount() {
		return (Integer) actionThreadLocal.get();
	}

	public static Integer reduct() {
		Integer count = getCount();
		if (count == null) {
			count = new Integer(1);
		}
		count--;
		actionThreadLocal.set(count);
		return count;
	}
}
