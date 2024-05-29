package com.shaba.games.model

//import javax.persistence.*

class Role {
    var id: Long? = null
    var name: String? = null

    constructor() {}
    constructor(name: String?) : super() {
        this.name = name
    }
}