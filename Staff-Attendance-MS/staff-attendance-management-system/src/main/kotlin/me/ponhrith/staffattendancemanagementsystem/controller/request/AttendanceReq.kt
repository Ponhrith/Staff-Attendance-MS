package me.ponhrith.staffattendancemanagementsystem.controller.request

import me.ponhrith.staffattendancemanagementsystem.model.AttendanceStatus
import java.time.LocalDate
import java.time.LocalTime

data class AttendanceReq(
    var username: String,
    var date: LocalDate,
    var time: LocalTime,
    var status: String,
    var checkedIn: Boolean,
)
