package com.fsp.festiplansb.model.repositories

import com.fsp.festiplansb.model.FestiplanUser
import com.fsp.festiplansb.model.Spectacle
import org.springframework.data.repository.CrudRepository

interface SpectacleRepository: CrudRepository<Spectacle, Long> {
    fun findAllByResponsable(responsable: FestiplanUser): List<Spectacle>
}