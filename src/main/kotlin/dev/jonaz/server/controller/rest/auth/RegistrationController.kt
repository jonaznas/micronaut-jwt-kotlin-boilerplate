package dev.jonaz.server.controller.rest.auth

import dev.jonaz.server.components.user.UserRegistration
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/registration")
@Secured(SecurityRule.IS_ANONYMOUS)
class RegistrationController {

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun post(
            @Parameter username: String,
            @Parameter password: String
    ): Any {
        val (success, message) = UserRegistration.usernamePasswordRegistration(username, password)

        return when (success) {
            true -> HttpStatus.OK
            else -> HttpResponse.badRequest(object {
                val message = message
            })
        }
    }
}
