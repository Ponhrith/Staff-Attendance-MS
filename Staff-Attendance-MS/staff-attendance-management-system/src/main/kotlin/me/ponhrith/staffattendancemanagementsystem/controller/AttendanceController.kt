package me.ponhrith.staffattendancemanagementsystem.controller

import me.ponhrith.staffattendancemanagementsystem.controller.request.AttendanceReq
import me.ponhrith.staffattendancemanagementsystem.controller.response.AttendanceRes
import me.ponhrith.staffattendancemanagementsystem.service.AttendanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/attendance")
@CrossOrigin("http://localhost:8084/")
class AttendanceController(@Autowired private val attendanceService: AttendanceService) {

    @CrossOrigin("http://localhost:8084/")
    @PostMapping
    fun checkAttendance(@RequestBody attendanceReq: AttendanceReq): AttendanceRes {
        return attendanceService.checkAttendance(attendanceReq)
    }
}
