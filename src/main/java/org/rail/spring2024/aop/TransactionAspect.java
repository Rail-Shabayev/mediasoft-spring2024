package org.rail.spring2024.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StopWatch;

@Aspect
@Component
@EnableTransactionManagement
public class TransactionAspect {

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        StopWatch watch = new StopWatch();
        joinPoint.proceed();
        watch.start();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            public void afterCommit() {
                watch.stop();
                var executionTime = watch.lastTaskInfo().getTimeMillis();
                System.out.println("Method's name: " + methodName + " | tx time: " + executionTime + " ms");
            }
        });
    }
}

