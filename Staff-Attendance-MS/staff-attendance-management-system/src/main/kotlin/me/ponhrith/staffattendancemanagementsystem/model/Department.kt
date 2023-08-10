package me.ponhrith.staffattendancemanagementsystem.model

import javax.persistence.*

@Entity
@Table(name = "departments")
data class Department(
    @Id @GeneratedValue()
    val id: Long,
    @Column(name = "name")
    val name: String,
)
