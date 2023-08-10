package me.ponhrith.staffattendancemanagementsystem.controller.request

import java.time.LocalDate
import java.util.*

data class AnnouncementReq(
    var content: String,
    var title: String,
    var effectiveDate: LocalDate,
    var expiredDate: LocalDate,
    var departmentIds: List<Long>,
)

// data class AnnouncementReq(
//    val id: Long,
//    val content: String,
//    val effectiveDate: LocalDate,
//    val expiredDate: LocalDate,
//    val title: String,
//    val user: UserAnnouncementReq,
//    val audience: List<AudienceReqDTO> // Use AudienceReqDTO instead of AudienceReq
// )
