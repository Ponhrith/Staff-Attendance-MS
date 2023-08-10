package me.ponhrith.staffattendancemanagementsystem.controller.response

import java.time.LocalDate

data class AnnouncementRes(
    var id: Long = 0,
    var content: String,
    var effectiveDate: LocalDate,
    var expiredDate: LocalDate,
    var title: String,
    var user: UserRes?,
    var audience: List<AudienceRes>? = null,
)
