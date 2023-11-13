package me.ponhrith.staffattendancemanagementsystem.controller.request

import me.ponhrith.staffattendancemanagementsystem.model.AttendanceStatus
import java.time.LocalDate

data class AttendanceReq(
    var username: String,
    var date: LocalDate,
    var status: String,
    var checkedIn: Boolean,
)
