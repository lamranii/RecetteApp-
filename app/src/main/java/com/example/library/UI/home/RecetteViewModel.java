package com.example.library.UI.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.library.Data.model.Recette;
import com.example.library.Data.repository.RecetteRepository;

import java.util.List;

public class RecetteViewModel extends AndroidViewModel {

    private RecetteRepository recetteRepository;

    public RecetteViewModel(@NonNull Application application) {
        super(application);
        recetteRepository = new RecetteRepository(application);
    }


    public LiveData<Recette> getRecetteById(int recetteId) {
        return recetteRepository.getRecetteById(recetteId);
    }
    public void deleteAllRecettes() {
        recetteRepository.deleteAll();
    }


    public void insertRecette(Recette recette) {
        recetteRepository.insert(recette);
    }

    public LiveData<List<Recette>> getAllRecettes() {
        return recetteRepository.getAllRecettes();
    }


}
