package me.ponhrith.staffattendancemanagementsystem.config

import me.ponhrith.staffattendancemanagementsystem.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userService: UserService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication?.name!!
        val password = authentication.credentials.toString()

        val user = userService.getUserByUsername(username)!!
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(user.role))
        return UsernamePasswordAuthenticationToken(username, password, authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(UsernamePasswordAuthenticationToken::class)!!
    }
}
