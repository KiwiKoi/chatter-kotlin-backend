package com.example.chatterkotlinbackend.repository

import com.example.chatterkotlinbackend.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long?> {
    fun findByPostId(id:String): List<Comment>
}