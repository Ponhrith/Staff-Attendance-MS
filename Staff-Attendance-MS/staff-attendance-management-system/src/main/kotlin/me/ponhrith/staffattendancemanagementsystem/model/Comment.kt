package me.ponhrith.staffattendancemanagementsystem.model

import javax.persistence.*

@Entity
@Table(name = "comments")
data class Comment(
    @Id @GeneratedValue()
    val id: Long,
    @Column(name = "announcement")
    val user_id: Long,
    @Column(name = "content")
    val content: String,
) {
    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
}
