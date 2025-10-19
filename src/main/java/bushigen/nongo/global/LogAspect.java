package bushigen.nongo.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
    /** コントローラーの実行前後にログを出力する  */
    @Around("execution(* bushigen.nongo.controller..*.*(..))")
    public Object startLog(ProceedingJoinPoint jp) throws Throwable {
        // 開始ログ出力
        System.out.println("\n===== Start API =====");
        System.out.println("Class: " + jp.getSignature().getDeclaringTypeName());
        System.out.println("Method: " + jp.getSignature().getName());

        try {
            // メソッド実行
            Object result = jp.proceed();

            System.out.println("===== API Ended =====\n");

            // 実行結果を呼び出し元に返却
            return result;
        } catch (Exception e) {
            log.error("XXXXX API Error XXXXX\n" + jp.getSignature());
            throw e;
        }
    }
}
