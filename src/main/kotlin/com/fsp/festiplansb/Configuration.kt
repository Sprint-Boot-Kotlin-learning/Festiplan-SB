package com.fsp.festiplansb

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.FestiplanUser.*
import com.fsp.festiplansb.model.repositories.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository) = ApplicationRunner {

        userRepository.save(
            FestiplanUser(
                id = 1L,
                firstname = "Françosi",
                lastname = "de Saint Palais",
                login = Username("login"),
                password = Password("mdp12345"),
                email = EmailAddress("test@test.fr")
            )
        )

    }
}