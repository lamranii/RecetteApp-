package com.example.library.Data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.library.Data.model.Recette;

import java.util.List;

@Dao
public interface RecetteDao {

    @Insert
    void insert(Recette recette);

    // Méthode pour récupérer les recettes d'un utilisateur spécifique
    @Query("SELECT * FROM recette_table WHERE userId = :userId")
    LiveData<List<Recette>> getRecettesByUser(int userId);

    @Delete
    void delete(Recette recette);

    // Récupérer toutes les recettes de tous les utilisateurs
    @Query("SELECT * FROM recette_table")
    LiveData<List<Recette>> getAllRecettes();

    @Query("SELECT * FROM recette_table WHERE id = :id")
    LiveData<Recette> getRecetteById(int id);

    @Query("DELETE FROM recette_table")
    void deleteAll();

}
