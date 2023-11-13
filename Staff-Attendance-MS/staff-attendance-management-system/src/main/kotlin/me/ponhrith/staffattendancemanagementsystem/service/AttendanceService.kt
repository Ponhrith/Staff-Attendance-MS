package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.request.AttendanceReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.AttendanceRes
import me.ponhrith.staffattendancemanagementsystem.model.Attendance
import me.ponhrith.staffattendancemanagementsystem.repository.AttendanceRepository
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class AttendanceService(
    @Autowired private val attendanceRepository: AttendanceRepository,
    @Autowired private val userRepository: UserRepository
) {

    fun checkAttendance(attendanceReq: AttendanceReq): AttendanceRes {
        // Find the user by their username
        val user = userRepository.findByUsername(attendanceReq.username)
            ?: throw NoSuchElementException("User not found")

        // Get the current date in GMT+7
        val currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"))
        val currentDate = currentDateTime.toLocalDate()

        // Check if the user has already checked in today
        val existingAttendance = attendanceRepository.findByUserAndDate(user, currentDate)

        if (existingAttendance != null) {
            // User has already checked in today, they cannot check in again until tomorrow
            return AttendanceRes(id = existingAttendance.id, user = null, date = currentDate, status = existingAttendance.status, checkedIn = existingAttendance.checked_in)
        }

        // Determine if the user is late based on the check-in time (e.g., after 9am GMT+7)
        val status = if (currentDateTime.toLocalTime() > LocalTime.of(9, 0)) {
            "Late"
        } else {
            "Present"
        }

        // Create a new attendance record for the user
        val newAttendance = Attendance(date = currentDate, status = status, checked_in = true)
        newAttendance.user = user

        // Save the attendance record to the repository
        val savedAttendance = attendanceRepository.save(newAttendance)

        return AttendanceRes(id = savedAttendance.id, user = null, date = savedAttendance.date, status = savedAttendance.status, checkedIn = savedAttendance.checked_in)
    }
}
