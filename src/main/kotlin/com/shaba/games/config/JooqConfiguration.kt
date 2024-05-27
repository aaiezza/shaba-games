package com.shaba.games.config

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource


@Configuration
class JooqConfig(@Autowired val dataSource: DataSource) {

    @Bean
    fun connectionProvider(): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(
            TransactionAwareDataSourceProxy(dataSource)
        )
    }

    @Bean
    fun dsl(): DSLContext {
        return DefaultDSLContext(configuration())
    }

    fun configuration(): DefaultConfiguration {
        val config = DefaultConfiguration()
        config.set(connectionProvider())
        config.set(SQLDialect.POSTGRES)
        return config
    }
}