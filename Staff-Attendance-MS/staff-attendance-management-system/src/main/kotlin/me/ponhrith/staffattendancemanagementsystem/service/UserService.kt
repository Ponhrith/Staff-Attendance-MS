package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.common.PasswordGenerator
import me.ponhrith.staffattendancemanagementsystem.common.isValidGender
import me.ponhrith.staffattendancemanagementsystem.common.isValidRole
import me.ponhrith.staffattendancemanagementsystem.common.isValidUsername
import me.ponhrith.staffattendancemanagementsystem.controller.request.UpdateUserReq
import me.ponhrith.staffattendancemanagementsystem.controller.request.UserReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.DepartmentRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.MessageRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.UserRes
import me.ponhrith.staffattendancemanagementsystem.exception.GeneralException
import me.ponhrith.staffattendancemanagementsystem.model.User
import me.ponhrith.staffattendancemanagementsystem.repository.DepartmentRepository
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val departmentRepository: DepartmentRepository,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun listUser(): List<UserRes> {
        val users = userRepository.findAll().toList()
        return users.map { user ->
            UserRes(
                id = user.id,
                username = user.username,
                gender = user.gender,
                role = user.role,
                department = DepartmentRes(user.department.id, user.department.name),
                email = user.email,
            )
        }
    }

    fun showUser(id: Long, auth: Authentication): UserRes {
        val currentUser = userRepository.findByUsername(auth.name)
        val user = userRepository.findById(id)

        if (user.isEmpty) throw GeneralException("No user found: $id")
        if (auth.authorities.first().toString() != "ROLE_ADMIN")
            if (currentUser?.id != id) throw GeneralException("You don't have permission to view other user data.")

        return user.get().let {
            UserRes(
                username = it.username,
                gender = it.gender,
                role = it.role,
                department = DepartmentRes(it.department.id, it.department.name),
                email = it.email,
            )
        }
    }

    fun getUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun createUser(userReq: UserReq): UserRes {
        validateUserRequest(userReq)

        // Get Department
        val department = departmentRepository.findById(userReq.departmentId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")
        }

        val passwordEncoder = BCryptPasswordEncoder()
        val generator = PasswordGenerator()
        val generatedPassword = generator.password(8)
        val encryptedPassword = passwordEncoder.encode(generatedPassword)
        log.info("The generated password is: $generatedPassword")

        // Initialize User
        val userEntity = User(
            username = userReq.username,
            gender = userReq.gender,
            role = userReq.role,
            password = encryptedPassword,
            email = userReq.email
        ).apply {
            this.department = department
        }

        // Save user
        val savedUser = userRepository.save(userEntity)
        log.info("$savedUser has been added")

        // Return user response
        return UserRes(
            id = savedUser.id,
            username = savedUser.username,
            gender = savedUser.gender,
            role = savedUser.role,
            department = DepartmentRes(savedUser.department.id, savedUser.department.name),
            password = generatedPassword,
            email = savedUser.email
        )
    }

    fun updateUser(id: Long, updateUserReq: UpdateUserReq): UserRes {
        validateUpdateUserRequest(updateUserReq)

        val existedUser = userRepository.findById(id)

        // Get Department
        val department = departmentRepository.findById(updateUserReq.departmentId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")
        }

        if (existedUser.isEmpty) throw GeneralException("No user found: $id")

        return existedUser.get().let {
            it.username = updateUserReq.username
            it.gender = updateUserReq.gender
            it.role = updateUserReq.role
            it.department = department
            it.email = updateUserReq.email
            userRepository.save(it)

            UserRes(
                username = it.username,
                gender = it.gender,
                role = it.role,
                department = DepartmentRes(it.department.id, it.department.name),
                email = it.email
            )
        }
    }

    fun deleteUser(id: Long): MessageRes {
        val existedUser = userRepository.findById(id)
        if (existedUser.isEmpty) throw GeneralException("No user found: $id")
        userRepository.deleteById(id)
        return MessageRes("User[${existedUser.get().username}] has been deleted.")
    }

    private fun validateUserRequest(userReq: UserReq) {
        userReq.username.isValidUsername()
        userReq.gender.isValidGender()
        userReq.role.isValidRole()
    }

    private fun validateUpdateUserRequest(updateUserReq: UpdateUserReq) {
        updateUserReq.username.isValidUsername()
        updateUserReq.gender.isValidGender()
        updateUserReq.role.isValidRole()
    }
}
