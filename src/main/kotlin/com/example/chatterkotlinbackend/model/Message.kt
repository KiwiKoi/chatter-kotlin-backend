package com.example.chatterkotlinbackend.model

import com.example.chatterkotlinbackend.model.Comment.LocalDateTimeDeserializer
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "messages")
data class Message(
    @Id @Column(name = "id") var id: String = UUID.randomUUID().toString(),
    @Column(name = "body") var body: String,
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @JsonIgnoreProperties("comments") @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    ) @JoinColumn(name = "user_id", referencedColumnName = "id") var author: User? = null,

    ) {

    override fun toString(): String {
        return ("Message(id=$id, body=$body, createdAt=$createdAt, author=$author)")
    }
}