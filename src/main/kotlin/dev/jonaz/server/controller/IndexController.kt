package dev.jonaz.server.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.security.utils.SecurityService
import java.security.Principal

@Controller("/test")
@Secured(SecurityRule.IS_AUTHENTICATED)
class IndexController {

    @Get("/")
    fun get(authentication: Authentication): Map<String, Boolean> {
        println(authentication.attributes.get("user"))
        return mapOf("test" to true)
    }
}
