package dev.jonaz.server.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Controller("/test")
@Secured(SecurityRule.IS_AUTHENTICATED)
class IndexController {

    @Get("/")
    fun get(principal: Principal): Map<String, Boolean> {
        return mapOf("test" to true)
    }
}
