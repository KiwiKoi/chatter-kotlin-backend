package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

import com.example.chatterkotlinbackend.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/users/{userId}")
    fun getUserById(@PathVariable userId: String):ResponseEntity<User>{
        return try {
            val userData = userRepository.findById(userId)
            userData.map { user -> ResponseEntity(user, HttpStatus.OK) }
                .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
        } catch (e: Exception){
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        }

}