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
	
	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declaration for controller
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	public void forControllerPackage() {}
	
	//setup pointcut declaration for service
		@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
		public void forServicePackage() {}
		
	//setup pointcut declaration for DAO
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	public void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	public void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		//display the method we are calling 
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("===> in @Before: calling method: " + method);
		
		//display the args to the method
		
		//get the args 
		Object[] args = joinPoint.getArgs();
		//loop through the args
		for(Object tempArg: args) {
			myLogger.info("========> Arguments: "+tempArg);
		}
	}
	
	//add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		//display the method we are calling 
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("===> in @AfterReturning: from method: " + method);
		
		//display data returned
		myLogger.info("====> Result: " + result);
		
	}
	
}
