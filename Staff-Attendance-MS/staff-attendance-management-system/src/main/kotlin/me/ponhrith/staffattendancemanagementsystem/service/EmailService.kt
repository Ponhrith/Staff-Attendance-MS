package me.ponhrith.staffattendancemanagementsystem.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

@Service
class EmailService @Autowired constructor(private val javaMailSender: JavaMailSender) {
    fun sendEmail(to: String, subject: String, text: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)

        try {
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(text)
        } catch (e: MessagingException) {
            e.printStackTrace()
        }

        javaMailSender.send(message)
    }
}
