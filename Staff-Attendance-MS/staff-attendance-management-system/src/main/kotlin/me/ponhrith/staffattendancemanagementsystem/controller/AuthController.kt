package me.ponhrith.staffattendancemanagementsystem.controller

import me.ponhrith.staffattendancemanagementsystem.controller.request.LoginReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.LoginRes
import me.ponhrith.staffattendancemanagementsystem.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:8080/")
class AuthController(private val authService: AuthService) {

    @CrossOrigin("http://localhost:8080/")
    @PostMapping("/login")
    fun login(@RequestBody loginReq: LoginReq): LoginRes {
        val username = loginReq.username
        val password = loginReq.password

        try {
            return authService.login(username, password)
        } catch (e: ResponseStatusException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")
        }
    }
}
