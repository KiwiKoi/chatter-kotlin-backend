package com.example.chatterkotlinbackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
@Table(name = "posts")
data class Post(
    @Id @Column(name = "id") var id: String = UUID.randomUUID().toString(),

    @Column(name = "body") var body: String? = null,

    @Column(name = "image") var image: String? = null,

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "published") var published: Boolean? = null,

    @JsonIgnoreProperties("posts") @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    ) @JoinColumn(name = "user_id", referencedColumnName = "id") var author: User? = null

){
    override fun toString() : String {
        return ( "Post(id=$id, body=$body, createdAt=$createdAt, updatedAt=$updatedAt, author=$author)")
    }

}
class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        return LocalDateTime.parse(parser.valueAsString, formatter)
    }
}