package com.fsp.festiplansb.controleur

import com.fsp.festiplansb.model.Categorie
import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.Festival
import com.fsp.festiplansb.model.repositories.CategorieRepository
import com.fsp.festiplansb.model.repositories.FestivalRepository
import com.fsp.festiplansb.model.repositories.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller()
class FestivalControleur(
    val session: HttpSession,
    val festivalRepository: FestivalRepository,
    val userRepository: UserRepository,
    val categorieRepository: CategorieRepository
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

    @GetMapping("/festivals/creer")
    fun creerFestival(
        model: Model
    ): String {

        //TODO remove STUB
        val user = userRepository.findUserByLogin(FestiplanUser.Username("login"))
        session.setAttribute("user", user)

        if (session.getAttribute("user") == null) {
            return "redirect:/login"
        }

        model["user"] = user
        model["categories"] = categorieRepository.findAll()

        return "festivals/creerFestival"
    }

    @PostMapping("/festivals/creer")
    fun creerFestivalPost(
        model: Model,
        @RequestParam nom: String,
        @RequestParam(required = false, defaultValue = "") description: String,
        @RequestParam dateDebut: String,
        @RequestParam dateFin: String,
        @RequestParam(defaultValue = "0") categorie: Long
    ): String {
        val erreurs = mutableListOf<String>()

        model["nom"] = nom
        model["description"] = description
        model["dateDebut"] = dateDebut
        model["dateFin"] = dateFin
        model["categorieSelectionner"] = categorie
        model["categories"] = categorieRepository.findAll()

        //TODO remove STUB
        val user = userRepository.findUserByLogin(FestiplanUser.Username("login"))
        session.setAttribute("user", user)

        if (session.getAttribute("user") == null) {
            return "redirect:/login"
        }

        val categorieOptional = categorieRepository.findById(categorie)
        val categorieTrouve = if (categorieOptional.isPresent) categorieOptional.get() else null
        if (categorieTrouve == null) {
            erreurs.add("Veiller selectionner une catégorie valide")
        }

        val dateDebutSaisie: LocalDate? = try {
            LocalDate.parse(dateDebut)
        } catch (e: Exception) {
            erreurs.add("Veiller saisir une date de début valide")
            null
        }

        val dateFinSaisie: LocalDate? = try {
            LocalDate.parse(dateFin)
        } catch (e: Exception) {
            erreurs.add("Veiller saisir une date de fin valide")
            null
        }

        if (erreurs.isNotEmpty()) {
            model["erreurs"] = erreurs
            return "festivals/creerFestival"
        }

        val festival = try {
            festivalRepository.save(
                Festival(
                    nom = nom,
                    description = description,
                    dateDebut = dateDebutSaisie!!,
                    dateFin = dateFinSaisie!!,
                    categorie = categorieTrouve!!,
                    organisateur = user!!,
                    equipeOrganisatrice = emptyList(),
                    scenes = emptyList(),
                    spectacles = emptyList()
                )
            )
        } catch (e: Exception) {
            erreurs.add(e.message ?: "Une erreur inconnue est survenue lors de la création du festival")
            model["erreurs"] = erreurs

            return "festivals/creerFestival"
        }

        return "redirect:/festivals/details/${festival.id}"
    }

    @GetMapping("/festivals/supprimer/{id}")
    fun supprimerFestival(
        model: Model,
        @PathVariable id: Long
    ): String {

        model["festival"] = festivalRepository.findById(id).orElse(null)
            ?: return "redirect:/festivals"

        return "festivals/supprimerFestival"
    }

    @PostMapping("/festivals/supprimer/{id}")
    fun supprimerFestivalPost(
        model: Model,
        @PathVariable id: Long
    ): String {

        val festival = festivalRepository.findById(id).orElse(null)
            ?: return "redirect:/festivals"

        festivalRepository.delete(festival)

        return "redirect:/festivals"
    }
}