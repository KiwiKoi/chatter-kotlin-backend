package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.Comment
import com.example.chatterkotlinbackend.repository.CommentRepository
import com.example.chatterkotlinbackend.repository.PostRepository
import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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

}