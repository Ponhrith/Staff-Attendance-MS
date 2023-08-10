package me.ponhrith.staffattendancemanagementsystem.common

import me.ponhrith.staffattendancemanagementsystem.model.User
import me.ponhrith.staffattendancemanagementsystem.repository.DepartmentRepository
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserDataLoader : CommandLineRunner {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var departmentRepository: DepartmentRepository

    override fun run(vararg args: String?) {
        seedUsers()
    }

    private fun seedUsers() {
        if (userRepository.count() == 0L) {
            departmentRepository.findById(1).orElseThrow {
                IllegalStateException("Department not found.")
            }
        }
        val passwordEncoder = BCryptPasswordEncoder()

        val user = User(
            id = 1,
            username = "Samantha",
            gender = "Female",
            role = "ADMIN",
            password = passwordEncoder.encode("password"),
        ).apply {
            department = departmentRepository.findById(1).get()
        }
        userRepository.save(user)
    }
}
