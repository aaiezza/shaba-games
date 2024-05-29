package com.shaba.games.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val dataSource: DataSource) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/signup",
                    "/css/**", "/js/**", "/images/**",
                    "/about", "/error"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .logout {
                it.logoutSuccessUrl("/login?logout&continue").permitAll()
            }
            .formLogin {
                it.loginPage("/login").permitAll()
            }
            .csrf { it.disable() }

        return http.build()
    }
}
