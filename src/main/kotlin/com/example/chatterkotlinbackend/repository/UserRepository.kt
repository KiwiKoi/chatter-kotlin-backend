package com.example.chatterkotlinbackend.repository

import com.example.chatterkotlinbackend.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface  UserRepository : JpaRepository<User, String> {
}