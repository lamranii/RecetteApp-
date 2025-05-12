 RecipeApp — Application Android de Gestion de Recettes
Une application mobile Android qui permet aux utilisateurs de :

consulter des recettes par catégorie,

afficher les détails d’une recette,

gérer les images associées aux recettes,

stocker les données localement à l’aide de Room.

 Fonctionnalités principales: 
 Liste des recettes avec titre, catégorie, et image

 Détail d’une recette à partir de l’identifiant
 Gestion d’images via URI (avec Glide)

 Stockage local avec Room

 Architecture MVVM (Model - View - ViewModel)

Dépendances utilisées :

Room — persistance locale

Glide — chargement d’images

LiveData & ViewModel — architecture MVVM 

Comment exécuter le projet ?
 
cloner git clone https://github.com/ton-utilisateur/RecipeApp.git

Ouvre-le dans Android Studio.

Compile et exécute sur un émulateur ou un appareil réel.


À venir: 
Ajout de la création et modification de recettes

Sauvegarde dans Firebase (optionnel)

Fonction de recherche par nom ou ingrédient