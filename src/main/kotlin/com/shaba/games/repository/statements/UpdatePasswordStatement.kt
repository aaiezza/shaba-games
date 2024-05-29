package com.shaba.games.repository.statements

import com.shaba.games.IllegalUsernameEmailException
import com.shaba.games.controller.UpdatePasswordRequest
import com.shaba.games.model.User
import com.shaba.games.repository.jooq.tables.User.Companion.USER
import org.jooq.DSLContext
import org.jooq.exception.IntegrityConstraintViolationException
import org.jooq.impl.DSL.currentOffsetDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

// TODO – Stop using and restructure the database to maintain user history (user_version table)
@Component
class UpdatePasswordStatement(
    @Autowired private val dsl: DSLContext,
    @Autowired private val getUserByEmailStatement: GetUserByEmailStatement
) {

    fun execute(user: User, updatePasswordRequest: UpdatePasswordRequest): User {
        try {
            dsl.transaction { context ->
                context.dsl().update(USER)
                    .set(USER.PASSWORD, updatePasswordRequest.newPassword)
                    .set(USER.UPDATED_AT, currentOffsetDateTime())
                    .where(USER.USERNAME.eq(user.username))
                    .execute()
            }

            return user.email?.let(getUserByEmailStatement::execute) ?: user
        } catch (e: IntegrityConstraintViolationException) {
            throw IllegalUsernameEmailException(e)
        }
    }
}