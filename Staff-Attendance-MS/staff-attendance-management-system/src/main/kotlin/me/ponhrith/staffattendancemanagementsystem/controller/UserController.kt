package me.ponhrith.staffattendancemanagementsystem.controller // package controller

import me.ponhrith.staffattendancemanagementsystem.controller.request.UpdateUserReq
import me.ponhrith.staffattendancemanagementsystem.controller.request.UserReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.MessageRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.UserRes
import me.ponhrith.staffattendancemanagementsystem.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:8080/")
class UserController(private val userService: UserService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @CrossOrigin("http://localhost:8080/")
    @GetMapping
    fun listUsers(): List<UserRes> {
        return userService.listUser()
    }

    @CrossOrigin("http://localhost:8080/")
    @GetMapping("/{id}")
    fun showUser(@PathVariable id: Long, auth: Authentication): UserRes {
        return userService.showUser(id, auth)
    }

    @CrossOrigin("http://localhost:8080/")
    @PostMapping
    fun createUser(@RequestBody userReq: UserReq): UserRes {
        return userService.createUser(userReq)
    }

    @CrossOrigin("http://localhost:8080/")
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updateUserReq: UpdateUserReq): UserRes {
        return userService.updateUser(id, updateUserReq)
    }

    @CrossOrigin("http://localhost:8080/")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): MessageRes {
        return userService.deleteUser(id)
    }
}
