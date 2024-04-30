package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.Comment
import com.example.chatterkotlinbackend.model.Post
import com.example.chatterkotlinbackend.model.User
import com.example.chatterkotlinbackend.repository.CommentRepository
import com.example.chatterkotlinbackend.repository.PostRepository
import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
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
        @RequestParam user_id: String,
        @RequestParam postId: String,
    ): ResponseEntity<Comment> {
        return try{
            val author: Optional<User> = userRepository.findById(user_id)
            val post: Optional<Post> = postRepository.findById(postId)
            if(author.isEmpty){
                return ResponseEntity(null, HttpStatus.BAD_REQUEST)
            }
            comment.author = author.get()
            comment.post = post.get()
            comment.createdAt = LocalDateTime.now()
            val newComment: Comment = commentRepository.save(comment)
            ResponseEntity(newComment, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            println("Error saving comment: ${e.message}")
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{postId}")
    fun getCommentsByPost(
        @PathVariable("postId") postId: String
    ): ResponseEntity<List<Comment>>{
        return try {
            val comments: List<Comment> = commentRepository.findByPostId(postId)

            if (comments.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(comments, HttpStatus.OK)
            }
        } catch(e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}