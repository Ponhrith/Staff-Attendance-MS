package me.ponhrith.staffattendancemanagementsystem.controller.request

import java.time.LocalDate

data class UpdateAnnouncementReq(
    var title: String,
    var content: String,
    var effectiveDate: LocalDate,
    var expiredDate: LocalDate,
    var departmentIds: List<Long>,
)
