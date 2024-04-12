package com.fsp.festiplansb.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Categorie(
    @Column(unique = true)
    val nom: String,

    @Column(unique = true)
    @Id @GeneratedValue val id: Long? = null
) {
}