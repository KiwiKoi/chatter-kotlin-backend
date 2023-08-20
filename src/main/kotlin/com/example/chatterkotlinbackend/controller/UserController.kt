package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.GoogleAuthUser
import com.example.chatterkotlinbackend.model.User
import com.example.chatterkotlinbackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): ResponseEntity<User> {
        return try {
            val userData = userRepository.findById(userId)
            userData.map { user -> ResponseEntity(user, HttpStatus.OK) }
                .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping
    fun createUser(
        @RequestBody googleAuthUser: GoogleAuthUser,
    ): ResponseEntity<User> {
        return try {
            val newUser = User(id = googleAuthUser.uid, email = googleAuthUser.email)
            val createdUser = userRepository.save(newUser)
            ResponseEntity(createdUser, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            println("Error saving user: ${e.message}")
            ResponseEntity<User>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/{userId}")
    fun updateUser(
        @PathVariable userId: String,
        @RequestBody updatedUser: User,
    ): ResponseEntity<User> {
        return try {
            val existingUser = userRepository.findById(userId)
            if (existingUser.isPresent) {
                val userToUpdate = existingUser.get()

                if(userToUpdate.email != updatedUser.email){
                    userToUpdate.email = updatedUser.email
                }
                if(userToUpdate.username != updatedUser.username){
                    userToUpdate.username = updatedUser.username
                }
                if(userToUpdate.firstname != updatedUser.firstname){
                    userToUpdate.firstname = updatedUser.firstname
                }
                if(userToUpdate.lastname != updatedUser.lastname){
                    userToUpdate.lastname = updatedUser.lastname
                }
                val updatedUserEntity = userRepository.save(userToUpdate)
                ResponseEntity(updatedUserEntity, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }
}