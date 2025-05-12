package com.example.library.UI.home;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.library.Data.model.Recette;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecetteDetailActivity extends AppCompatActivity {

    private TextView recipeNameTextView;
    private TextView recipeIngredientsTextView;
    private TextView recipeInstructionsTextView;
    private TextView recipeTimeTextView;
    private ImageView recipeImageView;
    private LinearLayout btnBack;
    private FloatingActionButton fab;

    private RecetteViewModel recetteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialiser les vues
        initViews();

        // Configurer les boutons de retour
        setupBackButtons();

        // Charger les détails de la recette
        loadRecipeDetails();
    }

    private void initViews() {
        recipeNameTextView = findViewById(R.id.recipe_name);
        recipeIngredientsTextView = findViewById(R.id.recipe_ingredients);
        recipeInstructionsTextView = findViewById(R.id.recipe_instructions);
        recipeTimeTextView = findViewById(R.id.recipe_time);
        recipeImageView = findViewById(R.id.recipe_image);

        fab = findViewById(R.id.fab);
    }

    private void setupBackButtons() {

        fab.setOnClickListener(v -> finish());
    }

    private void loadRecipeDetails() {
        recetteViewModel = new ViewModelProvider(this).get(RecetteViewModel.class);
        int recetteId = getIntent().getIntExtra("recette_id", -1);

        if (recetteId != -1) {
            recetteViewModel.getRecetteById(recetteId).observe(this, recette -> {
                if (recette != null) {
                    displayRecipeDetails(recette);
                }
            });
        }
    }

    private void displayRecipeDetails(Recette recette) {
        recipeNameTextView.setText(recette.getTitre());
        recipeIngredientsTextView.setText(recette.getIngredients());
        recipeInstructionsTextView.setText(recette.getInstructions());
        recipeTimeTextView.setText(recette.getTempsPreparation());

        // Chargement de l'image avec Glide
        String imageUri = recette.getImageUri();
        if (!TextUtils.isEmpty(imageUri)) {
            Glide.with(this)
                    .load(Uri.parse(imageUri)) // Utilise Uri.parse si c’est un URI local
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(recipeImageView);
        } else {
            recipeImageView.setImageResource(R.drawable.placeholder_image);
        }
    }
}
