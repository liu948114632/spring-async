package com.liu.service;


import com.liu.annotation.MyAnnotation;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/23
 */
@Component
@Aspect
public class Aop {
    private  static final Logger logger =Logger.getLogger(Aop.class);
    //切入点的配置.这里配置的是controller里面的所有类的所有方法。
    // 第一个*匹配类，第二个匹配方法。*后面的括号里面是参数个数.(..)表示任意个数的参数
    @Pointcut("execution(* com.liu.controller.*.*(..))")
    public void point(){

    }

    @Before(value = "point()")
    public void before(JoinPoint joinPoint){
        /*
        * 如果注解定义在接口的方法中，则使用
        * Class<?>[] class1 = joinPoint.getTarget().getClass().getInterfaces()获取所有的接口
        * 通过class1[0].getMethod(method, params);方法得到method
        * */
        //得到调用的类

        Class<?> class1 = joinPoint.getTarget().getClass();
        //调用的方法名称
        String method = joinPoint.getSignature().getName();
        //得到参数
        Class<?>[] params = ((MethodSignature)(joinPoint.getSignature())).getMethod().getParameterTypes();
        try {
            //根绝方法名称和参数得到方法
            Method m =class1.getMethod(method, params);

//            判断方法中是否有MyAnnotation注解
            if(m!= null && m.isAnnotationPresent(MyAnnotation.class)){
//获取注解。并得到里面的内容
                MyAnnotation annotation = m.getAnnotation(MyAnnotation.class);
                logger.info("这是我注解定义的参数："+annotation.value());
            }

        }catch (Exception e){
            logger.info(e);
        }
        logger.info(joinPoint.getSignature().getName()+"调用开始");
    }

    @After(value = "point()")
    public void after(JoinPoint joinPoint){
        logger.info(joinPoint.getSignature().getName()+"调用完毕");
    }
}
