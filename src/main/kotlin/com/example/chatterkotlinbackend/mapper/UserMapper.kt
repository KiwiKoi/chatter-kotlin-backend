package com.example.chatterkotlinbackend.mapper

import com.example.chatterkotlinbackend.dto.UserDTO
import com.example.chatterkotlinbackend.entity.UserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {

    fun toEntity(dto: UserDTO): UserEntity;


    fun toDto(entity: UserEntity): UserDTO;
}