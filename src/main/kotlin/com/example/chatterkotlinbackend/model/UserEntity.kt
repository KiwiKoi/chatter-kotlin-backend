package com.example.chatterkotlinbackend.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @Column(name="id")
    var id: String = UUID.randomUUID().toString(),

    @Column(name = "username")
    var username: String? = null,

    @Column(name = "email", unique = true)
    var email: String? = null,

    @Column(name = "firstname")
    var firstname: String? = null,

    @Column(name = "lastname")
    var lastname: String? = null,

    @OneToMany(
        mappedBy = "author",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
    )
    var posts: MutableList<PostEntity>? = null,
//
//
//    @OneToMany(
//    mappedBy = "author",
//    cascade = [CascadeType.ALL],
//    orphanRemoval = true
//    )
//    var comments: MutableList<Comment>? = null

) {
    override fun toString(): String {
        return ("User(id=$id, username=$username, firstname=$firstname, lastname=$lastname, email=$email)")
    }
}