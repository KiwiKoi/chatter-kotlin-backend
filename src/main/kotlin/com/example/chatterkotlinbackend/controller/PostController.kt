package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.PostEntity
import com.example.chatterkotlinbackend.model.UserEntity
import com.example.chatterkotlinbackend.repository.PostRepository
import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/posts/")
class PostController {
    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/")
    fun allPosts(): ResponseEntity<List<PostEntity>> {
        return try {
            val posts: List<PostEntity> = postRepository.findAll()
            if (posts.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(posts, HttpStatus.OK)
            }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping
    fun createPost(
        @RequestBody post: PostEntity,
        @RequestParam user_id: String
    ): ResponseEntity<PostEntity> {
        return try {
            val author: Optional<UserEntity> = userRepository.findById(user_id)
            if (author.isEmpty) {
                return ResponseEntity(null, HttpStatus.BAD_REQUEST)
            }
            post.author = author.get()
            post.published = false
            post.createdAt = LocalDateTime.now()
            val newPost: PostEntity = postRepository.save(post)
            ResponseEntity(newPost, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            println("Error saving post: ${e.message}")
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: String): ResponseEntity<PostEntity> {
        val postData: Optional<PostEntity> = postRepository.findById(id)
        return if (postData.isPresent) {
            ResponseEntity(postData.get(), HttpStatus.OK)
        } else {
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/user/{id}")
    fun getPostsByUser(@PathVariable id: String): ResponseEntity<List<PostEntity>> {
        return try {
            val posts: List<PostEntity> = postRepository.findByAuthorId(id)
            if (posts.isEmpty()) {
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(posts, HttpStatus.OK)
            }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: String
    ): ResponseEntity<Unit> {
        return try {
            val post = postRepository.findById(id)
            if (post.isPresent) {
                postRepository.delete(post.get())
                ResponseEntity(null, HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(null, HttpStatus.NOT_FOUND)
            }

        } catch (e: Exception){
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: String,
        @RequestBody updatedPost: PostEntity
    ): ResponseEntity<PostEntity> {
        return try {
            val existingPost = postRepository.findById(id)
            println("ID: $id")
            println("EXISTING POST: $existingPost")
            println("UPDATED POST: $updatedPost")
            if(existingPost.isPresent){
                val postToUpdate = existingPost.get()

                if(postToUpdate.body != updatedPost.body){
                    postToUpdate.body = updatedPost.body
                }
                if(postToUpdate.image != updatedPost.image){
                    postToUpdate.image = updatedPost.image
                }
                val updatedPostEntity = postRepository.save(postToUpdate)
                ResponseEntity(updatedPostEntity, HttpStatus.OK)

            } else{
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
