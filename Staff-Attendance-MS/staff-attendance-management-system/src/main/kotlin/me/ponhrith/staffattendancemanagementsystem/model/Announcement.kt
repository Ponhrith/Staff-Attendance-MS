package me.ponhrith.staffattendancemanagementsystem.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "announcements")
class Announcement(

    @Column(name = "title")
    var title: String,
    @Column(name = "content")
    var content: String,
    @Column(name = "view_count")
    val view_count: Long = 0,
    @Column(name = "effective_date")
    var effective_date: LocalDate,
    @Column(name = "expired_date")
    var expired_date: LocalDate,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_seq")
    @SequenceGenerator(name = "announcement_id_seq", sequenceName = "announcement_id_seq")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User

    @OneToMany(mappedBy = "announcement")
    var audience: Set<Audience> = setOf()
}
