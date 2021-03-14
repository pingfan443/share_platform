package com.zero.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/*
该类主要用于：当发生异常时，会被拦截并且把错误信息放到日志log中，会打印到控制台如ERROR：
同时也会放到前端error（自定义）页面，前端error只能看到错误信息，详情报错，开发人员可通过右键检查代码
即可查看（error页面做了处理）
 */
@ControllerAdvice//拦截所有标注有@Controller的控制器
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());//获取日志
    @ExceptionHandler(Exception.class)//表示拦截异常的方法，并且表示Exception级别都会被拦截
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        //记录日志信息，例如请求的url，异常信息（在控制台打印异常信息）
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);
        //如果存在ResponseStatus这个状态，让springboot自己处理异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        //前端页面可以获取url和异常信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
