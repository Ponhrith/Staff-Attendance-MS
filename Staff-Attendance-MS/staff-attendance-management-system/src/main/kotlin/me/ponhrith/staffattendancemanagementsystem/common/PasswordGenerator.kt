package me.ponhrith.staffattendancemanagementsystem.common
import java.util.*

class PasswordGenerator {

    fun password(len: Int): String {
        val alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&*?<>+"
        val random = Random()
        val stringBuilder = StringBuilder(len)
        for (i in 0 until len) {
            stringBuilder.append(alphanumeric[random.nextInt(alphanumeric.length)])
        }
        return stringBuilder.toString()
    }
}
