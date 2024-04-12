package com.fsp.festiplansb.model.repositories

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.Festival
import org.springframework.data.repository.CrudRepository

interface FestivalRepository: CrudRepository<Festival, Long> {
    fun findAllByOrganisateur(organisateur: FestiplanUser): List<Festival>
}