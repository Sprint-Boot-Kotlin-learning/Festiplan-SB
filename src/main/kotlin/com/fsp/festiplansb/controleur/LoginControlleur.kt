package com.fsp.festiplansb.controleur

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.repositories.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginControlleur (
    val userRepository: UserRepository,
    val session: HttpSession
) {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/login")
    fun loginPost(
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?,
        model : Model
    ): String {

        model["username"] = username
        model["password"] = password

        if (username.isNullOrEmpty()) {
            model["erreur"] = "Le nom d'utilisateur est vide"
            return "login"
        }

        if (password.isNullOrEmpty()) {
            model["erreur"] = "Le mot de passe est vide"
            return "login"
        }

        var usernameEstPresent: Boolean = false

        userRepository.findUserByLogin(FestiplanUser.Username(username))?.let {
            if (it.password.verify(password)) {
                session.setAttribute("user", it)
                return "redirect:/"
            }
            usernameEstPresent = true
        }

        if (usernameEstPresent) {
            model["erreur"] = "Le mot de passe est incorrect"
        } else {
            model["erreur"] = "Le nom d'utilisateur n'existe pas"
        }


        return "login"
    }

    @GetMapping("/deconnexion")
    fun deconnexion(): String {
        session.removeAttribute("user")
        return "redirect:/"
    }
}