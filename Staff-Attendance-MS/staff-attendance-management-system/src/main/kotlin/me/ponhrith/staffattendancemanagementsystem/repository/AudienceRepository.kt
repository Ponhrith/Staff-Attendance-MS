package me.ponhrith.staffattendancemanagementsystem.repository

import me.ponhrith.staffattendancemanagementsystem.model.Audience
import org.springframework.data.jpa.repository.JpaRepository

interface AudienceRepository : JpaRepository<Audience, Long> {
    fun findAllByAnnouncementIdAndDepartmentId(announcementId: Long, departmentId: Long): List<Audience>
    fun findAllByAnnouncementId(announcementId: Long): List<Audience>
    fun deleteAllByAnnouncementId(announcementId: Long)
    fun deleteAudienceByAnnouncementId(announcementId: Long)
}
