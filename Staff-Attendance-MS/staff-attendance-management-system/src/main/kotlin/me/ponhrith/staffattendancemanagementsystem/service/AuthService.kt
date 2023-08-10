package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.response.LoginRes
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun login(username: String, password: String): LoginRes {
        val user = userRepository.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password")

        val passwordEncoder = BCryptPasswordEncoder()
        if (!passwordEncoder.matches(password, user.password)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password")
        }
        return LoginRes(user.id, user.role)
    }
}
