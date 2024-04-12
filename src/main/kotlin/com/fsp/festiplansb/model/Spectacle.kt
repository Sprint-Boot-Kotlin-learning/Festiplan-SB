package com.fsp.festiplansb.model

import jakarta.persistence.*

@Entity
class Spectacle(
    val nom: String,
    val description: String,
    val duree: Int,
    @OneToOne val categorie: Categorie,
    val tailleScene: TailleScene,

    @Column(unique = false)
    @ManyToMany val intervenantSurScene: List<FestiplanUser>,

    @Column(unique = false)
    @ManyToMany val intervenantHorsScene: List<FestiplanUser>,

    @JoinColumn(name = "RESPONSABLE_ID", unique = false)
    @ManyToOne val responsable: FestiplanUser,
    @Id @GeneratedValue val id: Long? = null
) {
}