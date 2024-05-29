package com.shaba.games.model

class User {
    var id: Long? = null

    var email: String? = null
    var username: String? = null
    var password: String? = null

    var firstName: String? = null

    var lastName: String? = null

    var roles: Collection<Role>? = null

    constructor() {}
    constructor(
        email: String?,
        username: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        roles: Collection<Role>?,
    ) : super() {
        this.email = email
        this.username = username
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.roles = roles
    }
}