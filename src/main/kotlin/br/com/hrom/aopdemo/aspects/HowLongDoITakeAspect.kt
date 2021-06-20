package br.com.hrom.aopdemo.aspects

import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class HowLongDoITakeAspect {

    val log = KotlinLogging.logger { }

    @Around("@annotation(br.com.hrom.aopdemo.aspects.HowLongDoITake)")
    fun countTime(joinPoint: ProceedingJoinPoint): Any? {
        val targetClassName = joinPoint.signature.declaringType.name
        val method = joinPoint.signature.name

        val startTime = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val endTime = System.currentTimeMillis()
        log.info("$targetClassName's $method takes ${endTime - startTime}")
        return result
    }
}