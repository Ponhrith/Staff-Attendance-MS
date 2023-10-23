package me.ponhrith.staffattendancemanagementsystem.config

import me.ponhrith.staffattendancemanagementsystem.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {

    private val tokenValidityInSeconds = 30 * 24 * 60 * 60

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/api/v1/users/{id}").access("hasRole('USER') or hasRole('ADMIN')")
            .antMatchers("/api/v1/users/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
    }

//    override fun configure(http: HttpSecurity) {
//        http
//            .authorizeRequests()
//            // Your existing authorization rules
//            .antMatchers("/api/v1/users/{id}").access("hasRole('USER') or hasRole('ADMIN')")
//            .antMatchers("/api/v1/users/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//            .and()
//            .httpBasic()
//            .and()
//            .csrf().disable()
//            .rememberMe() // Enable "Remember Me"
//            .key("your-unique-key") // A unique key to identify the token
//            .userDetailsService(userDetailsService()) // Provide your UserDetailsService
//            .rememberMeParameter("remember-me") // The parameter name in your login form
//            .rememberMeCookieName("your-remember-me-cookie-name")
//            .tokenValiditySeconds(tokenValidityInSeconds) // Define token validity
//            .and()
//            .sessionManagement()
//            .sessionFixation().none()
//            .maximumSessions(1) // Enforce one session per user
//            .maxSessionsPreventsLogin(false) // New login invalidates old sessions
//    }



    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByUsername(username)
                ?.let { user ->
                    User.builder()
                        .username(user.username)
                        .password(user.password)
                        .roles(user.role)
                        .build()
                }
                ?: throw UsernameNotFoundException("User $username not found")
        }
    }
}
