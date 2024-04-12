package com.fsp.festiplansb.controleur

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.repositories.FestivalRepository
import com.fsp.festiplansb.model.repositories.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller()
class FestivalControleur(
    val session: HttpSession,
    val festivalRepository: FestivalRepository,
    val userRepository: UserRepository
) {

    @GetMapping("/festivals")
    fun listeFestivals(
        model: Model
    ): String {

        //TODO remove STUB
        val user = userRepository.findUserByLogin(FestiplanUser.Username("login"))
        session.setAttribute("user", user)

        if (session.getAttribute("user") == null) {
            return "redirect:/login"
        }

//        val user = session.getAttribute("user") as FestiplanUser

        val festivals = festivalRepository.findAllByOrganisateur(user!!)

        model["festivals"] = festivals

        return "festivals/listeFestivals"
    }

    @GetMapping("/festivals/details/{id}")
    fun detailsFestival(
        model: Model,
        @PathVariable id: Long
    ): String {

        val festival = festivalRepository.findById(id).orElse(null)
            ?: return "redirect:/festivals"

        model["festival"] = festival

        return "festivals/detailsFestival"
    }
}