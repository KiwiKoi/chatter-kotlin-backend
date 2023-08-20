package com.example.chatterkotlinbackend.repository

import com.example.chatterkotlinbackend.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<Post, Long?> {
    fun findById(id: String?): Optional<Post>
    fun findByAuthorId(id: String?): List<Post>
}
