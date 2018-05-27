package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.junit.internal.matchers.ThrowableCauseMatcher;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetService;
import edu.sjsu.cmpe275.aop.TweetStats;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;

// TODO: Auto-generated Javadoc
/**
 * The Class RetryAspect.
 */
@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	 
	
	public static boolean runAfterReturning=true;//flag to control follow and block advice
	
    
	/**
	 * Retry advice.
	 *
	 * @param joinPoint the join point
	 * @throws Throwable the throwable
	 */
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void retryAdvice(ProceedingJoinPoint joinPoint) throws Throwable  {	
		//System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {			
			joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch (IOException i) {
				try {
						//Attempting first Retry
						System.out.printf("First retry",joinPoint.getSignature().getName());
						joinPoint.proceed();
						System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
					
					}catch(IllegalArgumentException e1){
						e1.printStackTrace();
					}catch (IOException i1) {
						
							try {
									//Attempting second Retry
									System.out.printf("Second retry for method:",joinPoint.getSignature().getName());
									joinPoint.proceed();
									System.out.printf("Finished the execution of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
							
							}catch(IllegalArgumentException e3){
									e3.printStackTrace();
							}catch (IOException i3) {
							
									try{
										//Attempting third Retry
										System.out.printf("Third retry",joinPoint.getSignature().getName());
										joinPoint.proceed();
									
									}catch (Exception e) {
										runAfterReturning=false;
										e.printStackTrace();
									}
							}
					}
			}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
