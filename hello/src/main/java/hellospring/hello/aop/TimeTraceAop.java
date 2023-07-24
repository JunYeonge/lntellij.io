package hellospring.hello.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {
    @Around("execution( * hellospring.hello..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toLongString());
        try{
            return  joinPoint.proceed();
        } finally {
             long finish = System.currentTimeMillis();
             long timeMs = finish - start;
             System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }

}

    //@Aspect 어노테이션으로 AOP의 Advice 역할을 수행한다.
    // @Around("execution( * hellospring.hello..*(..))")
    //hellospring.hello 패키지와 그 하위 패키지에 속한
    // 모든 메서드들을 대상으로 AOP를 적용하겠다는 의미이다.

    // .execute 메서드는 @Around  어노테이션에 정의된 포인트컷(Pointcut)에
    // 해당하는 메서드들이 실행될 때 호출된다.

    // execute 메서드는 다음과 같은 작업을 수행한다.

    // System.currentTimeMillis()를 사용하며 메서드 실행시작 시간을 기록한다.
    // joinPoint,toString()을 사용하여 현재 실행되고 있는 메서드를
    // 문자열로 표현하며 로깅한다.

    // joinPoint.proceed()를 호출하여 대상 매서드를 실행한다.

    // 메서드 실행이 완료된 후, System.currentTimeMillis()를
    // 다시 사용하여 메서드 실행 종료 시간을 기록한다.

    // 실행 종료 시간에서 시작 시간을 빼서 메서드 실행 시간을 계산한다.

    // 메서드 실행시간을 로깅합니다.


    // TimeTraceAop 클래스는 hellospring.hello 패키지 하위 패키지에 속한 모든
    // 메서드들의 실행시간을 측정하고 이를 로그로 출력한다.
    // 이를 통해 애플리케이션의 성능 분석 및 모니터링에 도움이 될 수 있다.
