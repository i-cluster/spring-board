package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect			// AOP 지정 클래스
public class LoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
		[어드바이스(Advice)] - AOP 기능 구현 객체
		@Before: 타겟 메서드 호출 이전 적용
		@AfterReturning - 타겟 메서드 결과값 반환 후 적용
		@AfterThrowing - 타겟 메서드 예외 발생 시 적용
		@After - 타겟 메서드 예외 발생과 관계없이 적용
		@Around - 타겟 메서드 호출 이전과 이후 적용
		
		[포인트컷(pointcut)]
		execution(리턴타입 패키지경로.메서드명(파라미터))
	*/
	
	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		
		if (name.contains("Controller") == true) {
			type = "Controller ===> ";
		} else if (name.contains("Service") == true) {
			type = "Service ===> ";
		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}
		
		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
	
}
