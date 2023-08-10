package me.ponhrith.staffattendancemanagementsystem.model

import javax.persistence.*

@Entity
@Table(name = "attendance_status")
data class AttendanceStatus(
    @Id @GeneratedValue()
    val id: String,
    @Column(name = "name")
    val name: String,
)
