package me.ponhrith.staffattendancemanagementsystem.controller

import me.ponhrith.staffattendancemanagementsystem.controller.request.AnnouncementReq
import me.ponhrith.staffattendancemanagementsystem.controller.request.UpdateAnnouncementReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.AnnouncementRes
import me.ponhrith.staffattendancemanagementsystem.controller.response.MessageRes
import me.ponhrith.staffattendancemanagementsystem.service.AnnouncementService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/announcements")
@CrossOrigin("http://localhost:8084/")
class AnnouncementController(private val announcementService: AnnouncementService) {

    @CrossOrigin("http://localhost:8084")
    @GetMapping
    fun listAnnouncements(): List<AnnouncementRes> {
        return announcementService.listAnnouncement()
    }

    @CrossOrigin("http://localhost:8084")
    @GetMapping("/{id}")
    fun showAnnouncement(@PathVariable id: Long, auth: Authentication): ResponseEntity<AnnouncementRes> {
        val announcement = announcementService.showAnnouncement(id, auth)
        return ResponseEntity.ok(announcement)
    }

    @CrossOrigin("http://localhost:8084/")
    @PostMapping
    fun createAnnouncement(@RequestBody announcementReq: AnnouncementReq, auth: Authentication): AnnouncementRes {
        return announcementService.createAnnouncement(announcementReq, auth)
    }

    @CrossOrigin("http://localhost:8084/")
    @PutMapping("/{id}")
    fun updateAnnouncement(@PathVariable id: Long, @RequestBody updateAnnouncementReq: UpdateAnnouncementReq) {
        return announcementService.updateAnnouncement(id, updateAnnouncementReq)
    }

    @CrossOrigin("http://localhost:8084/")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): MessageRes {
        return announcementService.deleteAnnouncement(id)
    }
}
