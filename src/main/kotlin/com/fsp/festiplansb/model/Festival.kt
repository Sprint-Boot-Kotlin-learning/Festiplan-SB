package com.fsp.festiplansb.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Festival(
    val nom: String,

    @Column(length = 1000)
    val description: String,
    val dateDebut: LocalDate,
    val dateFin: LocalDate,
    @ManyToOne val categorie: Categorie,

    @ManyToMany val scenes: List<Scene>,

    @ManyToMany val spectacles: List<Spectacle>,

    @ManyToOne val organisateur: FestiplanUser,

    @ManyToMany val equipeOrganisatrice: List<FestiplanUser>,
    @Id @GeneratedValue val id: Long? = null
) {

    init {
        require(nom.isNotBlank()) { "Le nom du festival ne doit pas être vide" }
        require(description.length <= 1000) { "La description du festival ne doit pas dépasser 1000 caractères" }
        require(dateDebut.isBefore(dateFin)) { "La date de fin doit être postérieur à la date de début" }
    }
}