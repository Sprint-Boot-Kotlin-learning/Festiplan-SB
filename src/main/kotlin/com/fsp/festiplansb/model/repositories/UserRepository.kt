package com.fsp.festiplansb.model.repositories

import com.fsp.festiplansb.model.FestiplanUser
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<FestiplanUser, Long> {
    fun findUserByLogin(login: FestiplanUser.Username): FestiplanUser?
}