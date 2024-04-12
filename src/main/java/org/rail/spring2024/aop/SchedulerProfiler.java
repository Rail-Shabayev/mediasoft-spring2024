package org.rail.spring2024.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class SchedulerProfiler {

    @Pointcut("@annotation(org.rail.spring2024.annotation.NoteTime)")
    public void scheduleMethods() {
    }

    @Around("scheduleMethods()")
    public void profile(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        StopWatch watch = new StopWatch();
        try {
            watch.start();
        } finally {
            watch.stop();
            long executionTime = watch.lastTaskInfo().getTimeMillis();
            System.out.println(methodName + "'s time: " + executionTime + " ms");
        }
    }
}
