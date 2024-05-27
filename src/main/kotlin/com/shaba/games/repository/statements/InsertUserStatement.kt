package com.shaba.games.repository.statements

import com.shaba.games.model.Role
import com.shaba.games.model.User
import com.shaba.games.repository.jooq.tables.Role.Companion.ROLE
import com.shaba.games.repository.jooq.tables.User.Companion.USER
import com.shaba.games.repository.jooq.tables.UserRole.Companion.USER_ROLE
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InsertUserStatement(@Autowired private val dsl: DSLContext) {

    fun execute(user: User): User {
        dsl.transaction { context ->
            context.dsl().insertInto(USER)
                .columns(USER.USERNAME, USER.EMAIL, USER.PASSWORD, USER.FIRST_NAME, USER.LAST_NAME)
                .values(user.username, user.email, user.password, user.firstName, user.lastName)
                .execute()
            val newUserRecord = context.dsl().selectFrom(USER)
                .where(USER.EMAIL.eq(user.email))
                .fetchSingle()
            context.dsl().selectFrom(ROLE)
                .where(ROLE.NAME.`in`(user.roles?.map { it.name }))
                .fetch {
                    context.dsl().insertInto(USER_ROLE)
                        .columns(USER_ROLE.USER_ID, USER_ROLE.ROLE_ID)
                        .values(newUserRecord.get(USER.USER_ID), it.get(ROLE.ROLE_ID))
                        .execute()
                }
        }
        val newUser =
            dsl.selectFrom(USER
                    .leftJoin(USER_ROLE)
                        .using(USER.USER_ID)
                    .leftJoin(ROLE)
                        .using(ROLE.ROLE_ID))
                .where(USER.EMAIL.eq(user.email))
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
                }.first()

        return newUser
    }
}