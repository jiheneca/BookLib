# BookLib

Application Android de recherche et de gestion de livres favoris, développée en **Kotlin** avec **Jetpack Compose**.

## Description

BookLib permet de :

- consulter une liste de livres au démarrage ;
- rechercher un livre par mot-clé ;
- afficher le détail d'un livre (couverture, titre, auteurs, année de première publication, nombre d'éditions) ;
- ajouter ou retirer un livre de ses favoris ;
- consulter la liste de ses favoris, avec un compteur dans la barre du haut.

L'application gère aussi les états de chargement, d'erreur (avec un bouton « Réessayer ») et de liste vide.

## Technologies

| Outil | Rôle |
|---|---|
| Kotlin | Langage |
| Jetpack Compose + Material 3 | Interface utilisateur |
| Navigation 3 | Navigation entre les écrans |
| ViewModel + StateFlow | Gestion de l'état |
| Coil | Chargement des couvertures |
| API Open Library | Source des livres |

## Architecture

Le projet suit l'architecture **MVVM** (Model - View - ViewModel) :

```
Interface (Compose)  →  ViewModel  →  Repository  →  Source de données (API)
      écrans              état           accès            réseau
```

- **Model** : décrit les données (`Book`).
- **Data** : va chercher les livres (`BookRepository`).
- **ViewModel** : garde l'état de l'application (livres, chargement, erreur, favoris) et le met à disposition de l'interface via des `StateFlow`.
- **UI** : affiche l'état et envoie les actions de l'utilisateur au ViewModel.

Le flux de données est à sens unique : l'interface affiche l'état venant du ViewModel, et chaque action (recherche, favori) est renvoyée au ViewModel, qui met l'état à jour.

## Structure du projet

```
com.example.booklib
├── data
│   └── BookRepository.kt        Accès aux données
├── model
│   └── Book.kt                  Modèle d'un livre
├── viewmodel
│   └── BookViewModel.kt         État et logique (recherche, favoris)
├── navigation
│   ├── Destinations.kt          Destinations de la navigation
│   └── BookNavigation.kt        Barre du haut et affichage des écrans
├── ui
│   ├── theme                    Couleurs, typographie, thème
│   └── components               Éléments réutilisables
│       ├── BookCover.kt         Couverture d'un livre
│       ├── BookItem.kt          Carte d'un livre dans une liste
│       ├── SearchBar.kt         Champ de recherche
│       └── StateViews.kt        Chargement, erreur, liste vide
├── BookListScreen.kt            Écran de liste et de recherche
├── BookDetailScreen.kt          Écran de détail
├── FavorisScreen.kt             Écran des favoris
└── MainActivity.kt              Point d'entrée de l'application
```

## Écrans et navigation

| Écran | Rôle |
|---|---|
| `BookListScreen` | Recherche et liste des livres |
| `BookDetailScreen` | Détail d'un livre, ajout ou retrait des favoris |
| `FavorisScreen` | Liste des livres favoris |

La navigation utilise une pile (`backStack`) de destinations :

```
Liste ──► Détail ──► (retour)
  │
  └──► Favoris ──► Détail
```

L'icône cœur de la barre du haut ouvre l'écran des favoris et affiche leur nombre.

## Lancer le projet

1. Cloner le dépôt : `git clone https://github.com/jiheneca/BookLib.git`
2. Ouvrir le dossier dans Android Studio.
3. Attendre la synchronisation Gradle.
4. Lancer l'application sur un émulateur ou un téléphone (**Run ▶**).

Une connexion Internet est nécessaire pour charger les livres.

## Auteur

Projet réalisé par Vionaa dans le cadre du Master SWM, ISITCom Hammam-Sousse.
