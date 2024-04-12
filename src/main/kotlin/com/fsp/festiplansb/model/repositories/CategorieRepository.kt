package com.fsp.festiplansb.model.repositories

import com.fsp.festiplansb.model.Categorie
import org.springframework.data.repository.CrudRepository

interface CategorieRepository: CrudRepository<Categorie, Long> {
}