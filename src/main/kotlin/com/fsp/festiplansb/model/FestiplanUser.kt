package com.fsp.festiplansb.model

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.mindrot.jbcrypt.BCrypt

@Entity
class FestiplanUser(
    @Id @GeneratedValue val id: Long? = null,
    val firstname: String,
    val lastname: String,
    val login: Username,
    val email: EmailAddress,
    val password: Password
) {

    companion object {
        const val LOGIN_ALREADY_USED_ERROR: String = "Un utilisateur utilise déjà ce nom d'utilisateur."

        const val ACCOUNT_DOES_NOT_EXIST_ERROR: String = "Ce compte n'existe plus."
    }

    fun getFullName(): String = "$firstname $lastname"

    @Embeddable
    data class Username(val username: String?) {
        companion object {
            const val USERNAME_MIN_LENGTH: Int = 3
            const val USERNAME_MAX_LENGTH: Int = 15

            const val USERNAME_LENGTH_ERROR: String = "Le nom d'utilisateur doit contenir entre $USERNAME_MIN_LENGTH " +
                    "et $USERNAME_MAX_LENGTH caratères."

            fun isValidUsername(username: String?): Boolean {
                return username?.let {
                    it.length in USERNAME_MIN_LENGTH..USERNAME_MAX_LENGTH
                } ?: false
                // [..] operator does not exclude MIN and MAX values
                //     MIN..MAX
                // to exclude MIN and MAX, use following pattern:
                //     (MIN + 1) until MAX
            }
        }

        init {
            if (!isValidUsername(this.username)) {
                throw IllegalArgumentException(USERNAME_LENGTH_ERROR)
            }
        }
    }

    @Embeddable
    data class EmailAddress(val address: String?) {
        companion object {
            const val INVALID_EMAIL_ADDRESS_ERROR = "L'adresse email est invalide"

            fun isValidAddress(address: String?): Boolean {
                val emailAddressRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")

                return address?.matches(emailAddressRegex) ?: false
            }
        }

        init {
            if (!isValidAddress(this.address)) {
                throw IllegalArgumentException(
                    INVALID_EMAIL_ADDRESS_ERROR
                )
            }
        }
    }

    @Embeddable
    data class Password(private val givenPassword: String) {
        companion object {
            private const val PASSWORD_MIN_LENGTH: Int = 8
            // const = PRIMITIVE ON COMPILING
            // CANNOT BE DEFINED WITH CALCULATED VALUES OR DYNAMICALLY
            // Only on companion objects!!

            const val INVALID_PASSWORD_LENGTH_ERROR: String = "Votre mot de passe doit au moins contenir " +
                    "$PASSWORD_MIN_LENGTH caractéres."

            fun isValidPassword(password: String) = password.length >= PASSWORD_MIN_LENGTH
        }

        var password: String
            private set

        init {
            if (!isValidPassword(givenPassword)) {
                throw IllegalArgumentException(
                    INVALID_PASSWORD_LENGTH_ERROR
                )
            }

            this.password = BCrypt.hashpw(givenPassword, BCrypt.gensalt())
        }

        fun verify(password: String): Boolean = BCrypt.checkpw(password, this.password)
    }
}