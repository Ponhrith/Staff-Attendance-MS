package me.ponhrith.staffattendancemanagementsystem.service

import me.ponhrith.staffattendancemanagementsystem.controller.response.DepartmentRes
import me.ponhrith.staffattendancemanagementsystem.repository.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(private val departmentRepository: DepartmentRepository) {

    fun listDepartments(): List<DepartmentRes> {
        val departments = departmentRepository.findAll().toList()
        return departments.map { department ->
            DepartmentRes(
                id = department.id,
                name = department.name
            )
        }
    }
}
