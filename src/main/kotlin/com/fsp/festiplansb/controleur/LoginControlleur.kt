package com.fsp.festiplansb.controleur

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LoginControlleur () {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/login")
    fun loginPost(
        @RequestParam("username") login: String?,
        @RequestParam("password") password: String?,
        model : Model
    ): String {

        model["username"] = login
        model["password"] = password

        if (login.isNullOrEmpty()) {
            model["erreur"] = "Le nom d'utilisateur est vide"
            return "login"
        }

        if (password.isNullOrEmpty()) {
            model["erreur"] = "Le mot de passe est vide"
            return "login"
        }


        return "login"
    }
}