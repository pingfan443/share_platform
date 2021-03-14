package com.zero.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *该类主要用于；做切面处理。
 * 需求：每次访问Controller都会先到这里执行doBefore拿到日志info信息，
 * 最终返回请求时和返回是的信息。(打印到控制台，便于查看)
 */
@Aspect//进行切面操作
@Component//开启组件扫描，springboot通过组件扫描可以找到它
public class LogAspect {
    //拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //通过该注解声明他是一个切面，里面内容规定该切面拦截哪些类,则拦截Controller下面的所有的类以及方法
    @Pointcut("execution(* com.zero.Controller..*.*(..))")
    public void log() {}//* com.pingfan.Controller.*.*(..))表示的只有Controller包下的类以及所有方法


    @Before("log()")//括号里面log方法，也就是说在执行log之前会先执行该方法
    public void doBefore(JoinPoint joinPoint) {
//        logger.info("--------doBefore--------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //通过切面拿到
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //请求参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("--------doAfter--------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
