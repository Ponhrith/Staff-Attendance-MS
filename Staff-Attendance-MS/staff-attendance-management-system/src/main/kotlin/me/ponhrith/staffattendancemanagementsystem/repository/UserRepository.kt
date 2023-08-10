package me.ponhrith.staffattendancemanagementsystem.repository

import me.ponhrith.staffattendancemanagementsystem.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    override fun findById(id: Long): Optional<User>
    fun findByUsername(name: String): User?
    fun findUserIdByUsername(username: String): Long?
}
