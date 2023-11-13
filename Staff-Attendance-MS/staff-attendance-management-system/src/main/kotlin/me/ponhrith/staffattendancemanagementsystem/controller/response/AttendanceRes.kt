package me.ponhrith.staffattendancemanagementsystem.controller.response

import org.apache.catalina.User
import java.time.LocalDate
import java.time.LocalTime

data class AttendanceRes(
    var id: Long = 0,
    var user: UserRes?,
    var date: LocalDate,
    var time: LocalTime,
    var status: String,
    var checkedIn: Boolean,
)
