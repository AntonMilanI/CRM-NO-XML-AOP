package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.crm.controller.*.*(..))")
	private void forController() {}
	
	@Pointcut("execution(* com.crm.service.*.*(..))")
	private void forService() {}
	
	@Pointcut("execution(* com.crm.dao.*.*(..))")
	private void forDAO() {}
	
	@Pointcut("forController() || forService() || forDAO()")
	private void appFlow() {}
	
	@Before("appFlow()")
	public void before(JoinPoint jp) {
		String theMethod = jp.getSignature().toShortString();
		myLogger.info("=====> in @Before calling method: " + theMethod);
		
		Object[] arge = jp.getArgs();
		
		for(Object obj: arge) {
			myLogger.info("=======>arguments: " + obj);
		}
	}
	

	@AfterReturning(pointcut = "appFlow()", returning = "result")
	public void afterReturning(JoinPoint jp, Object result) {
		String theMethod = jp.getSignature().toShortString();
		myLogger.info("=====> in @After calling method: " + theMethod);
		
		myLogger.info("=========>> result: " + result);
	}
}
