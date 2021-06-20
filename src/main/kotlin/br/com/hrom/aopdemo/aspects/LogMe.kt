package br.com.hrom.aopdemo.aspects

import java.lang.annotation.ElementType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class LogMe(val logError: Boolean = false)
