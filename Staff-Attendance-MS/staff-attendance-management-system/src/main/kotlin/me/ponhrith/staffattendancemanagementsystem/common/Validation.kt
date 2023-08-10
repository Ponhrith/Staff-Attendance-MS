package me.ponhrith.staffattendancemanagementsystem.common

import me.ponhrith.staffattendancemanagementsystem.common.Constants.GENDERS
import me.ponhrith.staffattendancemanagementsystem.common.Constants.ROLES
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun String.isValidGender() {
    if (!GENDERS.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid gender")
}

fun String.isValidUsername() {
    // Only small letters, no number, no special chars, no space, only allow neither "-" nor "_" instead of space

    if (this.isEmpty())
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be empty")

    val regex = Regex("^[a-z\\-\\_]+$")
    if (!this.matches(regex)) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username, only lowercase letters")
    }
}

fun String.isValidRole() {
    if (!ROLES.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role")
}
