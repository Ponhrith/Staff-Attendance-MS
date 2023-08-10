package me.ponhrith.staffattendancemanagementsystem.controller.request

data class UserAnnouncementReq(
    var id: Long,
    var username: String,
    var gender: String,
    var role: String
)
