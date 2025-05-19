RecipeApp — Application Android de Gestion de Recettes
Une application mobile Android intuitive pour gérer et consulter des recettes culinaires, organisée par catégories. Elle permet de visualiser le détail des recettes, gérer les images associées, et stocker les données localement en toute simplicité.

Fonctionnalités principales
Liste des recettes
Affichage clair des recettes avec titre, catégorie, et image représentative.

Détail d’une recette
Consultation détaillée à partir de l’identifiant unique, avec tous les ingrédients et étapes.

Gestion des images
Chargement et affichage d’images via URI grâce à la bibliothèque Glide pour une gestion efficace des ressources.

Stockage local des données
Utilisation de Room pour sauvegarder et récupérer les données localement sur l’appareil.

Architecture MVVM
Implémentation du pattern Model-View-ViewModel pour une séparation claire des responsabilités et un code maintenable.

Technologies & Dépendances utilisées
Room — Persistance locale des données

Glide — Chargement et affichage performant des images

LiveData & ViewModel — Gestion réactive des données et cycle de vie grâce à l’architecture MVVM

Kotlin — Langage principal du projet

Comment exécuter le projet ?
Cloner le dépôt

bash
Copier
Modifier
git clone https://github.com/lamranii/RecetteApp-.git
Ouvrir dans Android Studio
Lance Android Studio, puis ouvre le dossier du projet cloné.

Compiler et exécuter

Configure un émulateur Android ou connecte un appareil physique.

Compile et lance l’application depuis Android Studio.

Fonctionnalités à venir
Création et modification des recettes
Ajout d’une interface pour permettre aux utilisateurs d’ajouter ou modifier des recettes.

Sauvegarde et synchronisation avec Firebase (optionnel)
Synchronisation des données en ligne pour accès multi-appareils.

Fonction de recherche avancée
Recherche par nom de recette ou ingrédient pour retrouver rapidement une recette.
