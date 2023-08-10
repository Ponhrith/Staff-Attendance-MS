package me.ponhrith.staffattendancemanagementsystem.model

import javax.persistence.*

@Entity
@Table(name = "attendance")
data class Attendance(
    @Id @GeneratedValue()
    val id: Long,
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "permission_id")
    lateinit var permission: Permission

    @ManyToOne
    @JoinColumn(name = "status_id")
    lateinit var status: AttendanceStatus
}
