package com.example.library.Data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.library.Data.Dao.RecetteDao;
import com.example.library.Data.Db.AppDatabase;
import com.example.library.Data.model.Recette;

import java.util.List;

public class RecetteRepository {

    private RecetteDao recetteDao;
    private LiveData<List<Recette>> allRecettes;

    public RecetteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        recetteDao = database.recetteDao();
        allRecettes = recetteDao.getAllRecettes();
    }

    public LiveData<List<Recette>> getAllRecettes() {
        return allRecettes;
    }

    public LiveData<Recette> getRecetteById(int id) {
        return recetteDao.getRecetteById(id);
    }

    public void insert(Recette recette) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            recetteDao.insert(recette);
        });
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            recetteDao.deleteAll();
        });
    }

}

