# BookLib

Application Android de recherche et de gestion de livres favoris, développée en **Kotlin** avec **Jetpack Compose**.

## Description

BookLib permet de :

- consulter une liste de livres au démarrage ;
- rechercher un livre par mot-clé avec le bouton « Rechercher » ;
- afficher le détail d'un livre (couverture, titre, auteurs, année de première publication, nombre d'éditions) ;
- ajouter ou retirer un livre de ses favoris ;
- consulter la liste de ses favoris, avec un compteur sur l'onglet « Favoris ».

L'application gère aussi les états de chargement, d'erreur (avec un bouton « Réessayer ») et de liste vide.


## Architecture

Le projet suit l'architecture **MVVM** (Model - View - ViewModel) :


- **Model** : décrit les données (`Book`).
- **Data** : va chercher les livres (`BookRepository`).
- **ViewModel** : garde l'état de l'application (livres, chargement, erreur, favoris) et le met à disposition de l'interface via des `StateFlow`.
- **UI** : affiche l'état et envoie les actions de l'utilisateur au ViewModel.

Le flux de données est à sens unique : l'interface affiche l'état venant du ViewModel, et chaque action (recherche, favori) est renvoyée au ViewModel, qui met l'état à jour.

## Structure du projet

```
com.example.booklib
├── data
│   └── BookRepository.kt           Accès aux données
├── model
│   └── Book.kt                     Modèle d'un livre
├── viewmodel
│   └── BookViewModel.kt            État et logique (recherche, favoris)
├── navigation
│   ├── Destinations.kt             Destinations de la navigation
│   └── BookNavigation.kt           Barres du haut et du bas, affichage des écrans
├── ui
│   ├── theme                       Couleurs, typographie, thème
│   ├── components                  Éléments réutilisables
│   │   ├── BookCover.kt            Couverture d'un livre
│   │   ├── BookItem.kt             Carte d'un livre dans une liste
│   │   ├── SearchBar.kt            Champ de recherche
│   │   └── StateViews.kt           Chargement, erreur, liste vide
│   ├── screens                     Un dossier par écran
│   │   ├── list/BookListScreen.kt          Recherche et liste des livres
│   │   ├── detail/BookDetailScreen.kt      Détail d'un livre
│   │   └── favorites/FavorisScreen.kt      Liste des favoris
│   └── util
│       └── BookExt.kt              Fonction utilitaire pour le texte des auteurs
└── MainActivity.kt                 Point d'entrée de l'application
```

## Écrans et navigation

| Écran | Rôle |
|---|---|
| Liste (`BookListScreen`) | Recherche et liste des livres |
| Détail (`BookDetailScreen`) | Détail d'un livre, ajout ou retrait des favoris |
| Favoris (`FavorisScreen`) | Liste des livres favoris |

La navigation utilise une pile (`backStack`) de destinations :

```
Accueil ──► Détail ──► (retour)
   │
   └──► Favoris ──► Détail
```

- Une **barre de navigation en bas** permet de passer de l'onglet « Accueil » à l'onglet « Favoris ». Un badge affiche le nombre de favoris.
- Une **barre en haut** affiche le titre de l'écran et une flèche de retour.

