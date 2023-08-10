package me.ponhrith.staffattendancemanagementsystem.repository

import me.ponhrith.staffattendancemanagementsystem.model.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : JpaRepository<Department, Long> {
    override fun getOne(id: Long): Department
}
