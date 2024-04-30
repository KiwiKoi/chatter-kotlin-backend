package com.example.chatterkotlinbackend.controller

import com.example.chatterkotlinbackend.model.Message
import com.example.chatterkotlinbackend.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class ChatController(private val messagingTemplate: SimpMessageSendingOperations) {

    @Autowired
    lateinit var messageRepository: MessageRepository

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    fun sendMessage(message: Message): Message{
        println("Received message: ${message.body}")

        val newMessage: Message = messageRepository.save(message)
        ResponseEntity(newMessage, HttpStatus.CREATED)
        return message
    }

    fun broadcastMessage(message: Message) {
        messagingTemplate.convertAndSend("/topic/messages", message as Any)
    }

}