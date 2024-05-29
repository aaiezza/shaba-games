package com.shaba.games.repository

import com.shaba.games.controller.UpdatePasswordRequest
import com.shaba.games.dto.UserRegistrationDto
import com.shaba.games.model.User
import com.shaba.games.repository.statements.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    @Autowired private val getUserByUsernameStatement: GetUserByUsernameStatement,
    @Autowired private val getUserByEmailStatement: GetUserByEmailStatement,
    @Autowired private val insertUserStatement: InsertUserStatement,
    @Autowired private val updateUserStatement: UpdateUserStatement,
    @Autowired private val updatePasswordStatement: UpdatePasswordStatement,
    @Autowired private val getAllUsersStatement: GetAllUsersStatement) {
    fun findByUsername(username: String): User? = getUserByUsernameStatement.execute(username)
    fun findByEmail(email: String): User? = getUserByEmailStatement.execute(email)

    fun save(user: User): User = insertUserStatement.execute(user)

    fun findAll(): List<User> = getAllUsersStatement.execute()
    fun updateUser(user: User, userRegistrationDto: UserRegistrationDto) : User = updateUserStatement.execute(user, userRegistrationDto)
    fun updatePassword(user: User, changePasswordRequest: UpdatePasswordRequest): User = updatePasswordStatement.execute(user, changePasswordRequest)
}
