package com.spring.common.util.tao;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description： 运行时异常监听，在运行时异常发生后，进行一些操作
 * Created by Ambitor on 2017/3/20.
 */
public interface RuntimeExceptionListener {

    void runtimeExceptionEvent(HttpServletRequest request, RuntimeException runtimeException);
}
