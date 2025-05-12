package com.example.library.UI.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Data.model.Recette;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class home extends AppCompatActivity {

    private RecyclerView recyclerViewRecettes;
    private RecetteAdapter recetteAdapter;
    private RecetteViewModel recetteViewModel;
    private FloatingActionButton fabBack;
    private Button btnAddRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerViewRecettes = findViewById(R.id.recyclerViewRecettes);
        fabBack = findViewById(R.id.fab);
        btnAddRecette = findViewById(R.id.btnAddRecette);

        recetteAdapter = new RecetteAdapter();
        recyclerViewRecettes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecettes.setAdapter(recetteAdapter);

        recetteViewModel = new ViewModelProvider(this).get(RecetteViewModel.class);

        // ✅ Supprimer toutes les anciennes recettes au démarrage

        recetteViewModel.deleteAllRecettes();
        // ✅ Ajouter les nouvelles recettes après suppression
        recetteViewModel.insertRecette(new Recette("Salade Marocaine", "Entrée","android.resource://com.example.library/" + R.drawable.img_20,                         "15 minutes", "Tomates, concombre, oignon", "Mélanger tous les ingrédients", 1));


        recetteViewModel.insertRecette(new Recette("Tajine au Poulet", "Plat principal",
                "android.resource://com.example.library/" + R.drawable.img_21,
                "1 heure", "Poulet, épices, légumes", "Cuire les ingrédients à feu doux", 1));

        recetteViewModel.insertRecette(new Recette("Pancakes", "Breakfast",
                "android.resource://com.example.library/" + R.drawable.img_pa,
                "20 minutes", "Farine, œufs, lait", "Mélanger les ingrédients et cuire dans une poêle", 1));



        // Observer les recettes
        recetteViewModel.getAllRecettes().observe(this, recettes -> {
            recetteAdapter.setRecetteList(recettes);
        });

        btnAddRecette.setOnClickListener(v -> {
            Intent addRecetteIntent = new Intent(home.this, AddRecetteActivity.class);
            startActivityForResult(addRecetteIntent, 1);
        });

        fabBack.setOnClickListener(v -> {
            startActivity(new Intent(home.this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("new_recette")) {
                Recette recetteAjoutee = (Recette) data.getSerializableExtra("new_recette");
                recetteViewModel.insertRecette(recetteAjoutee);
                Toast.makeText(this, "Recette ajoutée avec succès!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
