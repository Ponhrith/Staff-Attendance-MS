package me.ponhrith.staffattendancemanagementsystem.repository

import me.ponhrith.staffattendancemanagementsystem.model.Attendance
import me.ponhrith.staffattendancemanagementsystem.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AttendanceRepository : JpaRepository<Attendance, Long> {

    fun findByUserAndDate(user: User, date: LocalDate): Attendance?
    fun findByUser(user: User): List<Attendance>
}
