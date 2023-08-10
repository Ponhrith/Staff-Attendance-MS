package me.ponhrith.staffattendancemanagementsystem.model

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "permission")
data class Permission(
    @Id @GeneratedValue()
    val id: Long,
    @Column(name = "remarks")
    val remarks: String,
    @Column(name = "to_date")
    val to_date: Date,
    @Column(name = "approver_id")
    val approver_id: Long,
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
}
