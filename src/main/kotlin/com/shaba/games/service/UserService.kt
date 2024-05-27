package com.shaba.games.service

import org.springframework.security.core.userdetails.UserDetailsService
import com.shaba.games.dto.UserRegistrationDto
import com.shaba.games.model.User

interface UserService : UserDetailsService {
    fun save(registrationDto: UserRegistrationDto): User?
    fun registerNewUser(registrationDto: UserRegistrationDto): User? = save(registrationDto)
    val all: List<User?>?
}