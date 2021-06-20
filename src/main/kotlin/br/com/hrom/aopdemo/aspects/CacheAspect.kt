package br.com.hrom.aopdemo.aspects

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


@Order(HIGHEST_PRECEDENCE)
@Aspect
@Component
class CacheAspect(val objectMapper: ObjectMapper, val cache: Cache<String, String>) {

    @Around("@annotation(CacheMe)")
    fun countTime(joinPoint: ProceedingJoinPoint): Any? {
        val name = (joinPoint.signature as MethodSignature).method.annotations.first { it is CacheMe }.let { (it as CacheMe).name }
        val args = joinPoint.args.joinToString(prefix = ":", separator = ":") { it.toString() }
        val returnType = (joinPoint.signature as MethodSignature).method.returnType
        val cacheName = "$name$args"

        var result = cache.getIfPresent(cacheName)?.let { objectMapper.readValue(it, returnType) }

        if (result == null) {
            result = joinPoint.proceed()
            cache.put(cacheName, objectMapper.writeValueAsString(result))
        }

        return result
    }
}
