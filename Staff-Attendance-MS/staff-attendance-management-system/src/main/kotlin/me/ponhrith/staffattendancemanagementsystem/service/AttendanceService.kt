package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.request.AttendanceReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.AttendanceRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.DepartmentRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.UserRes
import me.ponhrith.staffattendancemanagementsystem.exception.GeneralException
import me.ponhrith.staffattendancemanagementsystem.model.Attendance
import me.ponhrith.staffattendancemanagementsystem.repository.AttendanceRepository
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
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

    // Function to list all attendance records
    fun listAttendance(): List<AttendanceRes> {
        val allAttendance = attendanceRepository.findAll()

        return allAttendance.map { attendance ->
            AttendanceRes(
                id = attendance.id,
                user = UserRes(
                    attendance.user.id,
                    attendance.user.username,
                    attendance.user.gender,
                    attendance.user.email,
                    department = DepartmentRes(
                        attendance.user.department.id,
                        attendance.user.department.name
                    )
                ),
                date = attendance.date,
                time = attendance.time,
                status = attendance.status,
                checkedIn = attendance.checked_in
            )
        }
    }

    // Add a function to get attendance records for a specific user
    fun listUserAttendance(username: String, auth: Authentication): List<AttendanceRes> {
        val requestingUser = userRepository.findByUsername(username)
            ?: throw NoSuchElementException("User not found")

        // Check if the authenticated user is an admin
        if (!auth.authorities.any { it.toString() == "ROLE_ADMIN" }) {
            // If not an admin, check if the authenticated user is requesting their own attendance
            val currentUser = userRepository.findByUsername(auth.name)
                ?: throw NoSuchElementException("User not found")

            if (currentUser.id != requestingUser.id) {
                throw GeneralException("You don't have permission to view other user's attendance records.")
            }
        }

        // Continue fetching and returning the attendance records for the requested user
        val userAttendance = attendanceRepository.findByUser(requestingUser)

        return userAttendance.map { attendance ->
            AttendanceRes(
                id = attendance.id,
                user = UserRes(
                    attendance.user.id,
                    attendance.user.username,
                    attendance.user.gender,
                    attendance.user.email,
                    department = DepartmentRes(
                        attendance.user.department.id,
                        attendance.user.department.name
                    )
                ),
                date = attendance.date,
                time = attendance.time,
                status = attendance.status,
                checkedIn = attendance.checked_in
            )
        }
    }



    // Function to show a specific attendance record
    fun showAttendance(id: Long): AttendanceRes {
        val attendance = attendanceRepository.findById(id)
            .orElseThrow { NoSuchElementException("Attendance record not found: $id") }

        return AttendanceRes(
            id = attendance.id,
            user = UserRes(
                attendance.user.id,
                attendance.user.username,
                attendance.user.gender,
                attendance.user.email,
                department = DepartmentRes(
                    attendance.user.department.id,
                    attendance.user.department.name
                )
            ),
            date = attendance.date,
            time = attendance.time,
            status = attendance.status,
            checkedIn = attendance.checked_in
        )
    }

    fun checkAttendance(attendanceReq: AttendanceReq): AttendanceRes {
        // Find the user by their username
        val user = userRepository.findByUsername(attendanceReq.username)
            ?: throw NoSuchElementException("User not found")

        // Get the current date and time in GMT+7
        val currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Bangkok"))
        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        // Check if the user has already checked in today
        val existingAttendance = attendanceRepository.findByUserAndDate(user, currentDate)

        if (existingAttendance != null) {
            // User has already checked in today, they cannot check in again until tomorrow
            return AttendanceRes(
                id = existingAttendance.id,
                user = UserRes(
                    existingAttendance.user.id,
                    existingAttendance.user.username,
                    existingAttendance.user.gender,
                    existingAttendance.user.email,
                    department = DepartmentRes(
                        existingAttendance.user.department.id,
                        existingAttendance.user.department.name
                    )
                ),
                date = currentDate,
                time = currentTime,
                status = existingAttendance.status,
                checkedIn = existingAttendance.checked_in)
        }

        // Determine if the user is late based on the check-in time (e.g., after 9am GMT+7)
        val status = if (currentTime > LocalTime.of(9, 0)) {
            "Late"
        } else {
            "Present"
        }

        // Create a new attendance record for the user
        val newAttendance = Attendance(date = currentDate, time = currentTime, status = status, checked_in = true)
        newAttendance.user = user

        // Save the attendance record to the repository
        val savedAttendance = attendanceRepository.save(newAttendance)

        return AttendanceRes(id = savedAttendance.id, user = null, date = savedAttendance.date, time = savedAttendance.time, status = savedAttendance.status, checkedIn = savedAttendance.checked_in)
    }
}
