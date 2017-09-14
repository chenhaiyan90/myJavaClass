package com.spring.common.util.tao;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description： 对RuntimeException 监听支持类
 * Created by Ambitor on 2017/3/20.
 */
public class RuntimeExceptionSupport {

    public RuntimeExceptionSupport(RuntimeExceptionListener[] listeners) {
        this.listeners = listeners;
    }

    /**
     * 触发监听
     * @param request
     * @param runtimeException
     */
    public void fireRuntimeExceptionEvent(HttpServletRequest request, RuntimeException runtimeException) {
        RuntimeExceptionListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].runtimeExceptionEvent(request, runtimeException);
    }

    /**
     * 添加对RuntimeException发生的事件进行监听
     * @param listener
     */
    public void addListeners(RuntimeExceptionListener listener) {
        synchronized (listeners) {
            RuntimeExceptionListener results[] = new RuntimeExceptionListener[listeners.length + 1];
            System.arraycopy(listeners, 0, results, 0, listeners.length);
            listeners = results;
        }
    }

    private RuntimeExceptionListener[] listeners = new RuntimeExceptionListener[0];
}
