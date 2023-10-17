package me.ponhrith.staffattendancemanagementsystem.controller.response

data class UserRes(
    var id: Long = 0,
    val username: String,
    val gender: String,
    val role: String,
    var department: DepartmentRes? = null,
    val password: String? = null,
    val email: String,
)
