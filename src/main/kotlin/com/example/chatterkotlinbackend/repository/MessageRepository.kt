package com.example.chatterkotlinbackend.repository

import com.example.chatterkotlinbackend.model.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Long> {

}