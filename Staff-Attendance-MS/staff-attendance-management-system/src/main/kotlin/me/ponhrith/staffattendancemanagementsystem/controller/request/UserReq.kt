package me.ponhrith.staffattendancemanagementsystem.controller.request

data class UserReq(
    var username: String,
    var gender: String,
    var role: String,
    var password: String?,
    var departmentId: Long
)
