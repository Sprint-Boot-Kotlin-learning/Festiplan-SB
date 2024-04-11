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

/**
 * Contrôleur pour la gestion de l'accès authentifié au site
 *
 * - GET /login : Affiche le formulaire de connexion
 * - POST /login : Traite le formulaire de connexion
 * - GET /deconnexion : Déconnecte l'utilisateur
 * - GET /createAccount : Affiche le formulaire de création de compte
 * - POST /createAccount : Traite le formulaire de création de compte
 */
@Controller
class LoginControlleur(
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
        model: Model
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

    @GetMapping("/createAccount")
    fun createAccount(): String {
        return "createAccount"
    }

    @PostMapping("/createAccount")
    fun createAccountPost(
        @RequestParam("firstname") firstname: String?,
        @RequestParam("lastname") lastname: String?,
        @RequestParam("username") username: String?,
        @RequestParam("email") email: String?,
        @RequestParam("password") password: String?,
        @RequestParam("password2") password2: String?,
        model: Model
    ): String {
        model["firstname"] = firstname
        model["lastname"] = lastname
        model["username"] = username
        model["email"] = email

        var erreurs: MutableList<String> = mutableListOf()

        if (firstname.isNullOrEmpty()) erreurs.add("Le prénom est vide")
        if (lastname.isNullOrEmpty()) erreurs.add("Le nom est vide")
        if (username.isNullOrEmpty()) erreurs.add("Le nom d'utilisateur est vide")
        if (email.isNullOrEmpty()) erreurs.add("L'adresse email est vide")
        if (password.isNullOrEmpty()) erreurs.add("Le mot de passe est vide")
        if (password != password2) erreurs.add("Les mots de passe ne correspondent pas")

        if (!FestiplanUser.Username.isValidUsername(username) ) {
            erreurs.add(FestiplanUser.Username.USERNAME_LENGTH_ERROR)
        } else if (userRepository.findUserByLogin(FestiplanUser.Username(username))  != null){
            erreurs.add(FestiplanUser.LOGIN_ALREADY_USED_ERROR)
        }

        if (!email?.let { FestiplanUser.EmailAddress.isValidAddress(it) }!!) erreurs.add(FestiplanUser.EmailAddress.INVALID_EMAIL_ADDRESS_ERROR)

        if (erreurs.isNotEmpty()) {
            model["erreurs"] = erreurs
            return "createAccount"
        }

        val user = FestiplanUser(
            firstname = firstname!!,
            lastname = lastname!!,
            login = FestiplanUser.Username(username),
            email = FestiplanUser.EmailAddress(email),
            password = FestiplanUser.Password(password!!)
        )

        userRepository.save(user)

        session.setAttribute("user", user)

        return "redirect:/"
    }


}