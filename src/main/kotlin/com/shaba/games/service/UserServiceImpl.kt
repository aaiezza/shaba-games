package com.shaba.games.service

import com.shaba.games.controller.UpdatePasswordRequest
import com.shaba.games.dto.UserRegistrationDto
import com.shaba.games.model.Role
import com.shaba.games.model.User
import com.shaba.games.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun save(registrationDto: UserRegistrationDto): User? {
        val user = User(
            registrationDto.email,
            registrationDto.username,
            passwordEncoder.encode(registrationDto.password),
            registrationDto.firstName,
            registrationDto.lastName,
            listOf(Role("ROLE_USER"))
        )
        return userRepository.save(user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Invalid username or password.")
        return org.springframework.security.core.userdetails.User(
            user.email, user.password,
            mapRolesToAuthorities(user.roles!!)
        )
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Invalid username or password.")
    }

    private fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?> {
        return roles.stream().map(Function<Role, SimpleGrantedAuthority?> { role: Role ->
            SimpleGrantedAuthority(
                role.name
            )
        }).collect(Collectors.toList())
    }

    override val all: List<User?>?
        get() = userRepository.findAll()

    override fun updateUser(user: User, userRegistrationDto: UserRegistrationDto): User {
        return userRepository.updateUser(user, userRegistrationDto)
    }

    // TODO
    override fun updatePassword(user: User, updatePasswordRequest: UpdatePasswordRequest): User {
        val passwordChangeRequest = UpdatePasswordRequest(
            passwordEncoder.encode(updatePasswordRequest.oldPassword),
            passwordEncoder.encode(updatePasswordRequest.newPassword)
        )
        // TODO - check to make sure the old password matches the user's current password
//        if (user.password != passwordChangeRequest.oldPassword) {
//            throw IllegalAccessException("Incorrect password")
//        }
        userRepository.updatePassword(user, passwordChangeRequest)

        return user
    }
}