package me.ponhrith.staffattendancemanagementsystem.repository

import me.ponhrith.staffattendancemanagementsystem.model.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AnnouncementRepository : JpaRepository<Announcement, Long> {
    override fun findById(id: Long): Optional<Announcement>
}
