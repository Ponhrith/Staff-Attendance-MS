package me.ponhrith.staffattendancemanagementsystem.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq")
    val id: Long = 0,
    @Column(name = "username")
    var username: String,
    @Column(name = "gender")
    var gender: String,
    @Column(name = "role")
    var role: String,
    @Column(name = "password")
    var password: String,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    lateinit var department: Department
}
