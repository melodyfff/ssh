package com.xinchen.ssh.core.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.xinchen.ssh.core.exception.ApplicationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class ApplicationExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = Logger.getLogger(ApplicationExceptionHandler.class);
    private static final String ERROR_INFO = "error_info";

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception ex) {


        JSONObject errJSON = new JSONObject();
        // 根据不同错误提示异常信息
        if (ex instanceof ApplicationException) {
            errJSON.put(ERROR_INFO, ex.getMessage());
        } else {
            errJSON.put(ERROR_INFO, "系统异常");
        }
        LOGGER.error(ex);


        // 判断是否 Ajax 请求
        if ((request.getHeader("accept").indexOf("application/json") > -1 ||
                (request.getHeader("X-Requested-With") != null &&
                        request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {

            try {
                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(ex.getMessage());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                LOGGER.warn(e);
            }
        } else {
            // 不是ajax请求返回页面
            ModelAndView mv = new ModelAndView("system/error");
            mv.addObject("errInfo",errJSON);
            return mv;
        }

        return null;
    }
}
