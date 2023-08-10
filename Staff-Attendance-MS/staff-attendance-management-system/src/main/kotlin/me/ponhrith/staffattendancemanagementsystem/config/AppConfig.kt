package me.ponhrith.staffattendancemanagementsystem.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class AppConfig {
    @Bean
    fun corsFilter(): FilterRegistrationBean<CorsFilter> {
        val registrationBean = FilterRegistrationBean<CorsFilter>()
        registrationBean.filter = CorsFilter()
        registrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return registrationBean
    }
}
