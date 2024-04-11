package com.fsp.festiplansb

import com.fsp.festiplansb.model.FestiplanUser
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Dispatcher(val session : HttpSession) {

    @GetMapping("/")
    fun index(model: Model): String {
        val user = session.getAttribute("user") as? FestiplanUser

        user?.let {
            model["user"] = it
        }

        return user?.let { "index" } ?: "login"
    }

}