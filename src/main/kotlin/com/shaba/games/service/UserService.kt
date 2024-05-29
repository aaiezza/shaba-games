package com.shaba.games.service

import com.shaba.games.controller.UpdatePasswordRequest
import org.springframework.security.core.userdetails.UserDetailsService
import com.shaba.games.dto.UserRegistrationDto
import com.shaba.games.model.User

interface UserService : UserDetailsService {
    fun loadUserByEmail(email: String): User
    fun save(registrationDto: UserRegistrationDto): User?
    fun registerNewUser(registrationDto: UserRegistrationDto): User? = save(registrationDto)
    val all: List<User?>?
    fun updateUser(user: User, userRegistrationDto: UserRegistrationDto): User
    fun updatePassword(user: User, updatePasswordRequest: UpdatePasswordRequest): User
}