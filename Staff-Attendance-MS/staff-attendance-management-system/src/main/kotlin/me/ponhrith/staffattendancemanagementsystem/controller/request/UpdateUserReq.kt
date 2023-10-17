package me.ponhrith.staffattendancemanagementsystem.controller.request

data class UpdateUserReq(
    var username: String,
    var gender: String,
    var role: String,
    var departmentId: Long,
    var email: String,
)
