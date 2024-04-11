package com.fsp.festiplansb

import com.fsp.festiplansb.model.User
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Dispatcher(val session : HttpSession) {

    @GetMapping("/")
    fun index(): String {
        val user = session.getAttribute("user") as? User

        return user?.let { "index" } ?: "login"
    }

}