package com.shaba.games.model

//import javax.persistence.*

//@Entity
//@Table(name = "user", uniqueConstraints = [UniqueConstraint(columnNames = ["email"]), UniqueConstraint(columnNames = ["username"])])
class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var email: String? = null
    var username: String? = null
    var password: String? = null

//    @Column(name = "first_name")
    var firstName: String? = null

//    @Column(name = "last_name")
    var lastName: String? = null

//    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
//    @JoinTable(
//        name = "users_roles",
//        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
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