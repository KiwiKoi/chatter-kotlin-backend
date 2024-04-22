package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.Comment
import com.example.chatterkotlinbackend.model.Post
import com.example.chatterkotlinbackend.repository.CommentRepository
import com.example.chatterkotlinbackend.repository.PostRepository
import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/comments/")
class CommentController {
    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var userRepository: UserRepository


    @PostMapping
    fun createComment(
        @RequestBody comment: Comment,
        @RequestParam user_id: String

    ){}

    @GetMapping
    fun getCommentsByPost(
        @PathVariable("postId") postId: String
    ): List<Comment>{
        return commentRepository.findByPostId(postId)
    }

}