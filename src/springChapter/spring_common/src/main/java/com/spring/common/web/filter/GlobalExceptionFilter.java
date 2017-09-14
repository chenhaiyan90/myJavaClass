package com.spring.common.web.filter;

import com.spring.common.exception.BusinessException;
import com.spring.common.exception.ExceptionMessage;
import com.spring.common.exception.RedirectException;
import com.spring.common.util.JacksonUtils;
import com.spring.common.util.JsonUtil;
import com.spring.common.util.tao.RuntimeExceptionSupport;
import com.spring.common.web.vo.ResponseVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 全局异常处理器
 * @User: ambitor_luo
 * @Date: 2016/5/31
 */
@ControllerAdvice
public class GlobalExceptionFilter {

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView runtimeExceptionHandler(RuntimeException runtimeException, HttpServletRequest request, HttpServletResponse response) {
        try {
            //触发监听
            if (runtimeExceptionSupport != null)
                runtimeExceptionSupport.fireRuntimeExceptionEvent(request, runtimeException);
            String requestMsg = createMessage(request);
            StringBuilder sb = new StringBuilder("请求异常-----GlobalException [" + requestMsg);
            sb.append("] Param:" + JacksonUtils.toJson(request.getParameterMap(), false));
            String msg = "操作失败，请重试或联系管理员";
            int code = -1;
            boolean notPrintStack = false;
            if (runtimeException instanceof BusinessException || runtimeException instanceof RedirectException) {
                //如果用户只填了code 则根据code去配置文件里取错误信息，如果配置文件没有该code，默认显示msg
                Object[] args = null;
                if (runtimeException instanceof BusinessException) {
                    BusinessException e = (BusinessException) runtimeException;
                    code = e.getCode();
                    args = e.getArgs();
                    notPrintStack = e.isNotPrintStack();
                } else if (runtimeException instanceof RedirectException) {
                    code = ((RedirectException) runtimeException).getCode();
                }
                if (StringUtils.isNotBlank(runtimeException.getMessage())) {//优先取用户填的Message
                    msg = runtimeException.getMessage();
                } else {
                    String propertiesMsg = ExceptionMessage.getErrorMessage(code);
                    if (args != null) propertiesMsg = MessageFormat.format(propertiesMsg, args);
                    msg = propertiesMsg == null ? msg : propertiesMsg;
                }
            }
            sb.append(" ResponseMsg：[error code:").append(code).append(" msg:").append(msg).append("]").append("\n");
            if (notPrintStack) logger.error("当前异常设置了不打印异常堆栈链信息 :" + sb.toString());
            else logger.error(sb.toString(), runtimeException);
            //如果需要跳转
            if (runtimeException instanceof RedirectException) {
                RedirectException redirectException = (RedirectException) runtimeException;
                String url = redirectException.getUrl();
                Map<String, String> json = new HashMap<String, String>();
                if (!"操作失败，请重试或联系管理员".equals(msg)) json.put("errmsg", msg);
                return new ModelAndView(url, json);
            } else {//JSON格式返回
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JsonUtil.object2Json(new ResponseVo(code, msg)));
                return new ModelAndView();
            }
        } catch (IOException e) {
            logger.error("全局异常处理返回IO报错:", e);
            throw runtimeException;
        }
    }

    protected String createMessage(HttpServletRequest request) {
        if (request == null || !(request instanceof ContentCachingRequestWrapper))
            request = new ContentCachingRequestWrapper(request);
        StringBuilder msg = new StringBuilder();
        msg.append("uri=").append(request.getRequestURL());
        msg.append('?').append(request.getQueryString());
        String client = request.getRemoteAddr();
        if (org.springframework.util.StringUtils.hasLength(client)) {
            msg.append(";client=").append(client);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            msg.append(";session=").append(session.getId());
        }
        String user = request.getRemoteUser();
        if (user != null) {
            msg.append(";user=").append(user);
        }
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 50000);
                String payload;
                try {
                    payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }
                msg.append(";payload=").append(payload.replaceAll("\r|\n| ", ""));
            }
        }
        return msg.toString();
    }

    @Autowired(required = false)
    private RuntimeExceptionSupport runtimeExceptionSupport;
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionFilter.class);
}
