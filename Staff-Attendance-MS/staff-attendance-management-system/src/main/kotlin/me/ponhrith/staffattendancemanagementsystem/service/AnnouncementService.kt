package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.request.AnnouncementReq
import me.ponhrith.staffattendancemanagementsystem.controller.request.UpdateAnnouncementReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.*
import me.ponhrith.staffattendancemanagementsystem.exception.GeneralException
import me.ponhrith.staffattendancemanagementsystem.model.Announcement
import me.ponhrith.staffattendancemanagementsystem.model.Audience
import me.ponhrith.staffattendancemanagementsystem.repository.AnnouncementRepository
import me.ponhrith.staffattendancemanagementsystem.repository.AudienceRepository
import me.ponhrith.staffattendancemanagementsystem.repository.DepartmentRepository
import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnnouncementService(
    private val announcementRepository: AnnouncementRepository,
    private val userRepository: UserRepository,
    private val audienceRepository: AudienceRepository,
    private val departmentRepository: DepartmentRepository
) {
    fun listAnnouncement(): List<AnnouncementRes> {
        val announcements = announcementRepository.findAll().toList()
        return announcements.map { announcement ->
            val audiences = audienceRepository.findAllByAnnouncementId(announcement.id)
            val audienceRes = audiences.map { audience ->
                AudienceRes(
                    id = audience.id,
                    department = DepartmentRes(
                        id = audience.department.id,
                        name = audience.department.name
                    )
                )
            }
            AnnouncementRes(
                id = announcement.id,
                title = announcement.title,
                content = announcement.content,
                effectiveDate = announcement.effective_date,
                expiredDate = announcement.expired_date,
                user = UserRes(
                    announcement.user.id,
                    announcement.user.username,
                    announcement.user.gender,
                    announcement.user.role,
                    department = DepartmentRes(
                        announcement.user.department.id,
                        announcement.user.department.name
                    )
                ),
                audience = audienceRes
            )
        }
    }

    fun showAnnouncement(id: Long, auth: Authentication): AnnouncementRes {
        val currentUser = userRepository.findByUsername(auth.name)
        val optAnnouncement = announcementRepository.findById(id)
        if (optAnnouncement.isEmpty) throw GeneralException("No announcement found: $id")
        val announcement = optAnnouncement.get()

        if (currentUser?.role == "ADMIN") {
            val allAudiences = audienceRepository.findAllByAnnouncementId(id)
            return announcementRes(announcement, allAudiences)
        }
        if (currentUser?.department == null) throw GeneralException("User doesn't have department")

        val departmentId = currentUser.department.id
        val announcementAudiences = audienceRepository.findAllByAnnouncementIdAndDepartmentId(id, departmentId)
        if (announcementAudiences.isEmpty()) throw GeneralException("User doesn't have permission to view announcement")

        return announcementRes(announcement, announcementAudiences)
    }

    private fun announcementRes(announcement: Announcement, audiences: List<Audience>): AnnouncementRes {
        return AnnouncementRes(
            id = announcement.id,
            title = announcement.title,
            content = announcement.content,
            effectiveDate = announcement.effective_date,
            expiredDate = announcement.expired_date,
            user = UserRes(
                announcement.user.id,
                announcement.user.username,
                announcement.user.gender,
                announcement.user.role,
                department = DepartmentRes(
                    announcement.user.department.id,
                    announcement.user.department.name
                )
            ),
            audience = audiences.map { audience ->
                AudienceRes(
                    id = audience.id,
                    department = DepartmentRes(
                        audience.department.id,
                        audience.department.name
                    )
                )
            }
        )
    }

    fun createAnnouncement(announcementReq: AnnouncementReq, auth: Authentication): AnnouncementRes {
        val currentUser = userRepository.findByUsername(auth.name)
        if (currentUser?.role != "ADMIN") {
            throw GeneralException("Only admins are allowed to create announcements")
        }

        val announcementEntity = Announcement(
            title = announcementReq.title,
            content = announcementReq.content,
            effective_date = announcementReq.effectiveDate,
            expired_date = announcementReq.expiredDate
        ).apply {
            this.user = currentUser
        }

        val savedAnnouncement = announcementRepository.save(announcementEntity)
        val audiences = mutableListOf<Audience>()

        if (announcementReq.departmentIds.isNotEmpty()) {
            for (departmentId in announcementReq.departmentIds) {
                val department = departmentRepository.findById(departmentId)
                if (department.isPresent) {
                    val audience = Audience().apply {
                        this.announcement = savedAnnouncement
                        this.department = department.get()
                    }
                    audiences.add(audienceRepository.save(audience))
                }
            }
        }
        return mapToAnnouncementRes(savedAnnouncement, audiences)
    }

    @Transactional
    fun updateAnnouncement(id: Long, updateAnnouncementReq: UpdateAnnouncementReq) {
        val existedAnnouncement = announcementRepository.findById(id)
            .orElseThrow { GeneralException("No announcement found: $id") }

        existedAnnouncement.apply {
            this.title = updateAnnouncementReq.title
            this.content = updateAnnouncementReq.content
            this.effective_date = updateAnnouncementReq.effectiveDate
            this.expired_date = updateAnnouncementReq.expiredDate
        }
        val savedAnnouncement = announcementRepository.save(existedAnnouncement)
        audienceRepository.deleteAllByAnnouncementId(id)
        if(updateAnnouncementReq.departmentIds.isNotEmpty()){
            for (departmentId in updateAnnouncementReq.departmentIds) {
                val department = departmentRepository.findById(departmentId)
                if (department.isPresent) {
                    val audience = Audience().apply {
                        this.announcement = savedAnnouncement
                        this.department = department.get()
                    }
                    audienceRepository.save(audience)
                }
            }
        }
    }
    
    @Transactional
    fun deleteAnnouncement(id: Long): MessageRes {
        val existedAnnouncement = announcementRepository.findById(id)
            .orElseThrow { GeneralException("No announcement found: $id") }

        val deletedAudiences = audienceRepository.deleteAllByAnnouncementId(id)
        announcementRepository.deleteById(id)

        return MessageRes("Announcement [${existedAnnouncement.id}] and $deletedAudiences audiences have been deleted.")
    }


    private fun mapToAnnouncementRes(announcement: Announcement, audiences: List<Audience>): AnnouncementRes {
        val audienceList = audiences.map { audience ->
            AudienceRes(
                id = audience.id,
                department = DepartmentRes(
                    id = audience.department.id,
                    name = audience.department.name
                )
            )
        }
        return AnnouncementRes(
            id = announcement.id,
            title = announcement.title,
            content = announcement.content,
            effectiveDate = announcement.effective_date,
            expiredDate = announcement.expired_date,
            user = UserRes(
                id = announcement.user.id,
                username = announcement.user.username,
                gender = announcement.user.gender,
                role = announcement.user.role,
                department = DepartmentRes(
                    id = announcement.user.department.id,
                    name = announcement.user.department.name
                )
            ),
            audience = audienceList
        )
    }
}
