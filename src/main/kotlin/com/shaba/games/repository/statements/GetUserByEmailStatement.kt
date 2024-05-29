package com.shaba.games.repository.statements

import com.shaba.games.model.Role
import com.shaba.games.model.User
import com.shaba.games.repository.jooq.tables.Role.Companion.ROLE
import com.shaba.games.repository.jooq.tables.User.Companion.USER
import com.shaba.games.repository.jooq.tables.UserRole.Companion.USER_ROLE
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@FunctionalInterface
@Component
class GetUserByEmailStatement(@Autowired private val dsl: DSLContext) {

    fun execute(email: String): User? {
        return dsl.selectFrom(
            USER
                .leftJoin(USER_ROLE)
                .using(USER.USER_ID)
                .leftJoin(ROLE)
                .using(ROLE.ROLE_ID)
        )
            .where(USER.EMAIL.eq(email))
            .fetchGroups(USER, ROLE)
            .map {
                User(
                    it.key.email,
                    it.key.username,
                    it.key.password,
                    it.key.firstName,
                    it.key.lastName,
                    it.value.map { role -> Role(role.name) }.toList()
                )
            }.firstOrNull()
    }
}