package br.com.hrom.aopdemo.aspects

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CacheMe(val name: String)
