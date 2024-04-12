package com.fsp.festiplansb.model.repositories

import com.fsp.festiplansb.model.Scene
import org.springframework.data.repository.CrudRepository

interface SceneRepository: CrudRepository<Scene, Long> {
}