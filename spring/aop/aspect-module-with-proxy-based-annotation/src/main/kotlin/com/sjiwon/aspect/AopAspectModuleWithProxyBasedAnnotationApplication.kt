package com.sjiwon.aspect

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AopAspectModuleWithProxyBasedAnnotationApplication

fun main(args: Array<String>) {
    runApplication<AopAspectModuleWithProxyBasedAnnotationApplication>(*args)
}
