package com.fsp.festiplansb

import com.fsp.festiplansb.model.*
import com.fsp.festiplansb.model.FestiplanUser.*
import com.fsp.festiplansb.model.repositories.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class Configuration {

    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        categorieRepository: CategorieRepository,
        festivalRepository: FestivalRepository,
        sceneRepository: SceneRepository,
        spectacleRepository: SpectacleRepository
    ) = ApplicationRunner {

        userRepository.save(
            FestiplanUser(
                id = 1L,
                firstname = "François",
                lastname = "de Saint Palais",
                login = Username("login"),
                password = Password("mdp12345"),
                email = EmailAddress("test@test.fr")
            )
        )
        userRepository.save(
            FestiplanUser(
                id = 2L,
                firstname = "Jean",
                lastname = "Dupont",
                login = Username("login2"),
                password = Password("mdp12345"),
                email = EmailAddress("jean.dupond@gmail.fr")
            )
        )
        userRepository.save(
            FestiplanUser(
                id = 3L,
                firstname = "Hubert",
                lastname = "Delaclasse",
                login = Username("hubert"),
                password = Password("hubert12$"),
                email = EmailAddress("hubert.delaclasse@iut-rodez.fr")
            )
        )

        categorieRepository.save(Categorie(id = 1L, nom = "Concert"))
        categorieRepository.save(Categorie(id = 2L, nom = "Piéce de thèatre"))
        categorieRepository.save(Categorie(id = 3L, nom = "Cirque"))
        categorieRepository.save(Categorie(id = 4L, nom = "Danse"))
        categorieRepository.save(Categorie(id = 5L, nom = "Projection de filme"))

        sceneRepository.save(
            Scene(
                nom = "Scene 1",
                taille = TailleScene.GRANDE,
                nbSpectateursMax = 1000,
                latitude = 45.0,
                longitude = 45.0
            )
        )
        sceneRepository.save(
            Scene(
                nom = "Scene 2",
                taille = TailleScene.MOYENNE,
                nbSpectateursMax = 500,
                latitude = 45.0,
                longitude = 45.0
            )
        )
        sceneRepository.save(
            Scene(
                nom = "Scene 3",
                taille = TailleScene.PETITE,
                nbSpectateursMax = 100,
                latitude = 45.0,
                longitude = 45.0
            )
        )
        sceneRepository.save(
            Scene(
                nom = "Scene 4",
                taille = TailleScene.GRANDE,
                nbSpectateursMax = 1000,
                latitude = 45.0,
                longitude = 45.0
            )
        )
        sceneRepository.save(
            Scene(
                nom = "Scene 5",
                taille = TailleScene.MOYENNE,
                nbSpectateursMax = 500,
                latitude = 45.0,
                longitude = 45.0
            )
        )

        spectacleRepository.save(
            Spectacle(
                nom = "Spectacle 1",
                description = "Description 1",
                duree = 60,
                categorie = categorieRepository.findById(1).get(),
                tailleScene = TailleScene.GRANDE,
                intervenantSurScene = listOf(userRepository.findById(1).get()),
                intervenantHorsScene = listOf(userRepository.findById(1).get()),
                responsable = userRepository.findById(1).get()
            )
        )
        spectacleRepository.save(
            Spectacle(
                nom = "Spectacle 2",
                description = "Description 2",
                duree = 120,
                categorie = categorieRepository.findById(2).get(),
                tailleScene = TailleScene.MOYENNE,
                intervenantSurScene = listOf(userRepository.findById(1).get()),
                intervenantHorsScene = listOf(userRepository.findById(1).get()),
                responsable = userRepository.findById(1).get()
            )
        )
        spectacleRepository.save(
            Spectacle(
                nom = "Spectacle 3",
                description = "Description 3",
                duree = 90,
                categorie = categorieRepository.findById(3).get(),
                tailleScene = TailleScene.PETITE,
                intervenantSurScene = listOf(userRepository.findById(1).get()),
                intervenantHorsScene = listOf(userRepository.findById(1).get()),
                responsable = userRepository.findById(1).get()
            )
        )

        festivalRepository.save(
            Festival(
                nom = "Festival 1",
                description = "Description 1",
                dateDebut = LocalDate.now(),
                dateFin = LocalDate.now().plusDays(10),
                scenes = sceneRepository.findAll().toList(),
                spectacles = spectacleRepository.findAll().toList(),
                organisateur = userRepository.findById(1).get(),
                equipeOrganisatrice = listOf(userRepository.findById(1).get()),
                categorie = categorieRepository.findById(1).get()
            )
        )
        festivalRepository.save(
            Festival(
                nom = "Festival 2",
                description = "Description 2",
                dateDebut = LocalDate.now(),
                dateFin = LocalDate.now().plusDays(10),
                scenes = sceneRepository.findAll().toList(),
                spectacles = spectacleRepository.findAll().toList(),
                organisateur = userRepository.findById(1).get(),
                equipeOrganisatrice = userRepository.findAll().toList(),
                categorie = categorieRepository.findById(2).get()
            )
        )
        festivalRepository.save(
            Festival(
                nom = "Festival 3",
                description = "Description 3",
                dateDebut = LocalDate.now(),
                dateFin = LocalDate.now().plusDays(10),
                scenes = sceneRepository.findAll().toList(),
                spectacles = spectacleRepository.findAll().toList(),
                organisateur = userRepository.findById(1).get(),
                equipeOrganisatrice = listOf(userRepository.findById(1).get()),
                categorie = categorieRepository.findById(3).get()
            )
        )

    }
}