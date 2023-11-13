package me.ponhrith.staffattendancemanagementsystem.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "attendance")
data class Attendance(
    @Id @GeneratedValue()
    var id: Long = 0,
    @Column(name = "date")
    var date: LocalDate,
    @Column(name = "status")
    var status: String,
    @Column(name = "checked_in")
    var checked_in: Boolean,

) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User

}
