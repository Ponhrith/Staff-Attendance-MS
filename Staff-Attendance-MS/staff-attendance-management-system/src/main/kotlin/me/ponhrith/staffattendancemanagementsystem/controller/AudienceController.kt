package me.ponhrith.staffattendancemanagementsystem.controller

import me.ponhrith.staffattendancemanagementsystem.controller.response.AudienceRes
import me.ponhrith.staffattendancemanagementsystem.service.AudienceService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/audiences")
@CrossOrigin("http://localhost:8080/")
class AudienceController(val audienceService: AudienceService) {

    @CrossOrigin("http://localhost:8080/")
    @GetMapping
    fun listAudiences(): List<AudienceRes> {
        return audienceService.listAudience()
    }
}