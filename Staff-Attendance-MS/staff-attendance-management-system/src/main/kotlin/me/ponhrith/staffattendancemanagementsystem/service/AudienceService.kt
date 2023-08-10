package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.response.AudienceRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.DepartmentRes
import me.ponhrith.staffattendancemanagementsystem.repository.AnnouncementRepository
import me.ponhrith.staffattendancemanagementsystem.repository.AudienceRepository
import org.springframework.stereotype.Service

@Service
class AudienceService(
    private val audienceRepository: AudienceRepository,
) {
    fun listAudience(): List<AudienceRes>{
        val audiences = audienceRepository.findAll().toList()
        return audiences.map{audience ->
            AudienceRes(
                id = audience.id,
                department = DepartmentRes(
                    id = audience.department.id,
                    name = audience.department.name
                )
            )
        }
    }
}