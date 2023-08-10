package me.ponhrith.staffattendancemanagementsystem

import me.ponhrith.staffattendancemanagementsystem.config.AppConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StaffAttendanceManagementSystemApplication

fun main(args: Array<String>) {
	runApplication<StaffAttendanceManagementSystemApplication>(*args)
	AppConfig()
}
