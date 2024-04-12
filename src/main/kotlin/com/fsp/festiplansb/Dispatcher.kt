package com.fsp.festiplansb

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.repositories.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Dispatcher(
    val session : HttpSession,
    val userRepository: UserRepository) {

    @GetMapping("/")
    fun index(model: Model): String {
        //val user = session.getAttribute("user") as? FestiplanUser

        //TODO remove STUB
        val user = userRepository.findUserByLogin(FestiplanUser.Username("login"))
        session.setAttribute("user", user)

        user?.let {
            model["user"] = it
        }

        return user?.let { "index" } ?: "login"
    }

    @GetMapping("/creer")
    fun creer(model: Model): String {
        //val user = session.getAttribute("user") as? FestiplanUser

        //TODO remove STUB
        val user = userRepository.findUserByLogin(FestiplanUser.Username("login"))
        session.setAttribute("user", user)

        user?.let {
            model["user"] = it
        }

        return user?.let { "creer" } ?: "login"
    }

}