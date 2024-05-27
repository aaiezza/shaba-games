package com.shaba.games.dto

class UserRegistrationDto {
    var email: String? = null
    var username: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null

    constructor() {}
    constructor(
        email: String?,
        username: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
    ) : super() {
        this.email = email
        this.username = username
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
    }
}