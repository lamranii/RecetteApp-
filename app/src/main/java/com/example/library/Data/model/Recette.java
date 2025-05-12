package com.example.library.Data.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.Serializable;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Entity(tableName = "recette_table")
public class Recette implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titre;
    private String categorie;
    private String imageUri; // Chemin vers une image dans le téléphone
    private String tempsPreparation;
    private String ingredients;
    private String instructions;
    private int userId; // Pour lier la recette à un utilisateur

    // Constructeur
    public Recette(String titre, String categorie, String imageUri, String tempsPreparation, String ingredients, String instructions, int userId) {
        this.titre = titre;
        this.categorie = categorie;
        this.imageUri = imageUri;
        this.tempsPreparation = tempsPreparation;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.userId = userId;
    }



    // Getters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTempsPreparation() {
        return tempsPreparation;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setTempsPreparation(String tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Implémentation de la méthode getImageUrl
    public byte[] getImageUrl() {
        File imageFile = new File(imageUri);
        if (imageFile.exists()) {
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                Bitmap bitmap = BitmapFactory.decodeStream(fis);

                // Convertir le Bitmap en byte[]
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
