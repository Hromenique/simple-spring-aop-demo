package br.com.hrom.aopdemo.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    @Around("@annotation(LogMe)")
    fun log(joinPoint: ProceedingJoinPoint): Any? {
        val targetClassName = joinPoint.signature.declaringType
        val method = joinPoint.signature.name
        val parameterNames = (joinPoint.signature as MethodSignature).parameterNames
        val values = joinPoint.args
        val paramsAndValues = parameterNames.mapIndexed { index, param ->
            "$param=${values.getOrNull(index)}"
        }.joinToString(separator = ", ")
        val logErrors = (joinPoint.signature as MethodSignature).method.annotations.first { it is LogMe }.let { (it as LogMe).logError }

        val logger = LoggerFactory.getLogger(targetClassName)

        try {
            logger.info("method=$method, stage=init, $paramsAndValues")
            val result = joinPoint.proceed()
            logger.info("method=$method, stage=end, $paramsAndValues, result=$result")
            return result
        } catch (ex: Throwable) {
            if (logErrors)
                logger.error("method=$method, stage=error, $paramsAndValues, error_class=${ex::class.java.name}, error_message=${ex.message}", ex)

            throw ex
        }
    }
}