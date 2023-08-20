package com.example.chatterkotlinbackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "posts")
data class Post(
    @Id @Column(name = "id") var id: String = UUID.randomUUID().toString(),

    @Column(name = "title") var title: String? = null,

    @Column(name = "body") var body: String? = null,

    @Column(name = "image") var image: String? = null,

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "published") var published: Boolean? = null,

    @JsonIgnoreProperties("posts") @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL]
    ) @JoinColumn(name = "user_id", referencedColumnName = "id") var author: User? = null

){
    override fun toString() : String {
        return ( "Post(id=$id, title=$title, body=$body, author=$author)")
    }
}
