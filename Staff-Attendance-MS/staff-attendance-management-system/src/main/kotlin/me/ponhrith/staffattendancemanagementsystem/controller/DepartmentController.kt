package me.ponhrith.staffattendancemanagementsystem.controller

import me.ponhrith.staffattendancemanagementsystem.controller.response.DepartmentRes
import me.ponhrith.staffattendancemanagementsystem.service.DepartmentService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/departments")
@CrossOrigin("http://localhost:8084/")
class DepartmentController(val departmentService: DepartmentService) {

    @CrossOrigin("http://localhost:8084/")
    @GetMapping
    fun listDepartments(): List<DepartmentRes> {
        return departmentService.listDepartments()
    }
}
