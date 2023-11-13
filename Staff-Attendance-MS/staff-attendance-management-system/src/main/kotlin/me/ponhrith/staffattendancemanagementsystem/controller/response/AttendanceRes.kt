package me.ponhrith.staffattendancemanagementsystem.controller.response

import org.apache.catalina.User
import java.time.LocalDate

data class AttendanceRes(
    var id: Long = 0,
    var user: UserRes?,
    var date: LocalDate,
    var status: String,
    var checkedIn: Boolean,
)
