package com.fsp.festiplansb.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Scene(
    val nom: String,
    val taille: TailleScene,
    val nbSpectateursMax: Int,
    val latitude: Double,
    val longitude: Double,
    @Id @GeneratedValue val id: Long? = null
) {
}

enum class TailleScene {
    PETITE,
    MOYENNE,
    GRANDE;

    override fun toString(): String {
        return when (this) {
            PETITE -> "Petite"
            MOYENNE -> "Moyenne"
            GRANDE -> "Grande"
        }
    }
}