package me.ponhrith.staffattendancemanagementsystem.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name = "audience")
data class Audience(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audience_id_seq")
    @SequenceGenerator(name = "audience_id_seq", sequenceName = "audience_id_seq")
    var id: Long? = null,
) {
    @ManyToOne
    @JoinColumn(name = "department_id")
    lateinit var department: Department

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    @JsonBackReference
    lateinit var announcement: Announcement
}
