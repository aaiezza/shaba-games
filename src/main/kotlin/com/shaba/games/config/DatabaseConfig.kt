package com.shaba.games.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
class DatabaseConfig {
    @ConfigurationProperties("app.datasource")
    @Bean
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun passwordEncrypter(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
