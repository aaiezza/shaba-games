package com.shaba.games.repository

import com.shaba.games.model.User
import com.shaba.games.repository.statements.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    @Autowired private val getUserByUsernameStatement: GetUserByUsernameStatement,
    @Autowired private val insertUserStatement: InsertUserStatement,
    @Autowired private val getAllUsersStatement: GetAllUsersStatement) {
    fun findByUsername(username: String): User? = getUserByUsernameStatement.execute(username)

    fun save(user: User): User = insertUserStatement.execute(user)

    fun findAll(): List<User> = getAllUsersStatement.execute()
}
