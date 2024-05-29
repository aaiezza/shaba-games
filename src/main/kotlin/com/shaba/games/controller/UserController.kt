package com.shaba.games.controller

import com.shaba.games.IllegalUsernameEmailException
import com.shaba.games.dto.UserRegistrationDto
import com.shaba.games.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class UserController(private val userService: UserService) {

    @GetMapping("/signup")
    fun signUpForm(model: Model): String {
        model.addAttribute("userRegistrationDto", UserRegistrationDto())
        return "signup"
    }

    @PostMapping("/signup")
    fun signUp(@ModelAttribute("userRegistrationDto") userRegistrationDto: UserRegistrationDto, model: Model): String {
        try {
            userService.registerNewUser(userRegistrationDto)
        } catch (e: IllegalUsernameEmailException) {
            model.addAttribute("error", e.message)
            return "signup"
        }
        return "redirect:/login"
    }

    @PostMapping("/update-user")
    fun updateUser(
        @ModelAttribute("userRegistrationDto") userRegistrationDto: UserRegistrationDto,
        model: Model,
        @AuthenticationPrincipal userDetails: UserDetails
    ): String {
        val user = userService.loadUserByEmail(userDetails.username)
        try {
            val updatedUser = userService.updateUser(user, userRegistrationDto)
            model.addAttribute("user", updatedUser)
            return if(updatedUser.email == user.email) "redirect:/profile" else "redirect:/logout"
        } catch (e: IllegalUsernameEmailException) {
            model.addAttribute("error", e.message)
            return "profile"
        }
    }

    @PostMapping("/update-password")
    fun updateUser(
        @ModelAttribute("passwordUpdate") updatePasswordRequest: UpdatePasswordRequest,
        model: Model,
        @AuthenticationPrincipal userDetails: UserDetails
    ): String {
        val user = userService.loadUserByEmail(userDetails.username)
        try {
            val updatedUser = userService.updatePassword(user, updatePasswordRequest)
            model.addAttribute("user", updatedUser)
            return if(updatedUser.email == user.email) "redirect:/profile" else "redirect:/logout"
        } catch (e: IllegalUsernameEmailException) {
            model.addAttribute("error", e.message)
            return "profile"
        }
    }

    @GetMapping("/login")
    fun loginForm(): String {
        return "login"
    }

    @GetMapping("/logout")
    fun logoutPage(request: HttpServletRequest?, response: HttpServletResponse?): String {
        SecurityContextHolder.getContext().authentication.let {
            SecurityContextLogoutHandler().logout(request, response, it)
        }
        return "redirect:login?logout&continue"
    }

    @GetMapping("/about")
    fun aboutPage(): String {
        return "about"
    }

    @GetMapping("/profile")
    fun profilePage(model: Model, @AuthenticationPrincipal userDetails: UserDetails): String {
        model.addAttribute("user", userService.loadUserByEmail(userDetails.username))
        model.addAttribute("passwordUpdate", UpdatePasswordRequest())
        return "profile"
    }
}


