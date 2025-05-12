package com.example.library.UI.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.library.Data.model.Recette;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;

public class AddRecetteActivity extends AppCompatActivity {

    private TextInputEditText editTextTitle, editTextCategory, editTextPrepTime,
            editTextIngredients, editTextInstructions;
    private ImageView imageViewRecipe;
    private FloatingActionButton fabBack;
    private Button buttonSaveRecipe;

    private Uri imageUri;
    private RecetteViewModel recipeViewModel;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    handleImageSelection(result.getData());
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openImagePicker();
                } else {
                    showPermissionRationale();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        initViews();
        setupViewModel();
        setupClickListeners();

        // Charger l’image par défaut au début
        loadImageWithGlide(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_image_placeholder));
    }

    private void initViews() {
        editTextTitle = findViewById(R.id.editTextTitre);
        editTextCategory = findViewById(R.id.editTextCategorie);
        editTextPrepTime = findViewById(R.id.editTextPrepTime);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        imageViewRecipe = findViewById(R.id.imageViewRecette);
        fabBack = findViewById(R.id.fabBack);
        buttonSaveRecipe = findViewById(R.id.buttonSaveRecette);
    }

    private void setupViewModel() {
        recipeViewModel = new ViewModelProvider(this).get(RecetteViewModel.class);
    }

    private void setupClickListeners() {
        fabBack.setOnClickListener(v -> finish());
        buttonSaveRecipe.setOnClickListener(v -> saveRecipe());
        imageViewRecipe.setOnClickListener(v -> checkPermissionsAndOpenPicker());
    }

    private void checkPermissionsAndOpenPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(Intent.createChooser(intent, "Choisir une image"));
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'ouverture de la galerie", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImageSelection(Intent data) {
        try {
            imageUri = data.getData();
            if (imageUri != null) {
                Log.d("IMAGE_URI", "URI sélectionné: " + imageUri.toString());

                if (isGooglePhotosUri(imageUri)) {
                    imageUri = handleGooglePhotosUri(imageUri);
                }

                if (imageUri != null) {
                    loadImageWithGlide(imageUri);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur de traitement de l'image", Toast.LENGTH_SHORT).show();
            Log.e("IMAGE_ERROR", "Erreur de chargement", e);
        }
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private Uri handleGooglePhotosUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                inputStream.close();
                return uri;
            }
        } catch (Exception e) {
            Log.e("GOOGLE_PHOTOS", "Erreur avec Google Photos", e);
        }
        return null;
    }

    private void loadImageWithGlide(Uri uri) {
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_broken_image)
                .centerCrop()
                .into(imageViewRecipe);
    }

    private void showPermissionRationale() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES :
                Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission requise")
                    .setMessage("Cette permission est nécessaire pour sélectionner des images")
                    .setPositiveButton("Autoriser", (d, w) -> checkPermissionsAndOpenPicker())
                    .setNegativeButton("Annuler", null)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Permission requise")
                    .setMessage("Veuillez activer la permission dans les paramètres")
                    .setPositiveButton("Paramètres", (d, w) -> openAppSettings())
                    .setNegativeButton("Annuler", null)
                    .show();
        }
    }

    private void openAppSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Impossible d'ouvrir les paramètres", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();
        String prepTime = editTextPrepTime.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();

        if (validateInput(title, category, prepTime, ingredients, instructions)) {

            String imagePath = (imageUri != null)
                    ? imageUri.toString()
                    : Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.to).toString();

            Recette recipe = new Recette(
                    title,
                    category,
                    prepTime,
                    imagePath,
                    ingredients,
                    instructions,
                    1 // ID utilisateur temporaire
            );

            recipeViewModel.insertRecette(recipe);
            setResult(RESULT_OK);
            Toast.makeText(this, "Recette enregistrée avec succès", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
