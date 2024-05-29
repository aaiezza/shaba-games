package com.shaba.games.controller

class UpdatePasswordRequest {
    var oldPassword: String? = null
    var newPassword: String? = null

    constructor()
    constructor(
        oldPassword: String?,
        newPassword: String?,
    ) : super() {
        this.oldPassword = oldPassword
        this.newPassword = newPassword
    }
}