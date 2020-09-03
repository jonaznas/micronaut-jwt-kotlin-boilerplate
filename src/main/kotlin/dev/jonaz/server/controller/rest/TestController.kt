package dev.jonaz.server.controller.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule

@Controller("/test")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TestController {

    @Get
    fun get(authentication: Authentication): Any {
        val userId = authentication.attributes.get("userId")

        return HttpResponse.ok(object {
            val message = "Your user id is $userId"
        })
    }
}
