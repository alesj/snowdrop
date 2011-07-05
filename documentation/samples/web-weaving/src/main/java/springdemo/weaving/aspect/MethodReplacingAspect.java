package springdemo.weaving.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;

@Aspect
public class MethodReplacingAspect {

    @Pointcut("execution(* *..HelloService.doInternal(..))")
    public void allInternalMethods() {
    }

    @Around("allInternalMethods()")
    public String replaceCall(ProceedingJoinPoint pjp) {
        return "Invocation replaced";
    }
}
