package com.example.library.UI.home;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.library.Data.model.Recette;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;

public class RecetteAdapter extends RecyclerView.Adapter<RecetteAdapter.RecetteViewHolder> {

    private List<Recette> recetteList = new ArrayList<>();

    public void setRecetteList(List<Recette> recettes) {
        this.recetteList = recettes;
        notifyDataSetChanged();
    }

    @Override
    public RecetteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecetteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecetteViewHolder holder, int position) {
        Recette recette = recetteList.get(position);
        holder.titreTextView.setText(recette.getTitre());
        holder.categorieTextView.setText(recette.getCategorie());

        String imageUri = recette.getImageUri();

        Log.d("RecetteAdapter", "URI = " + imageUri); // Pour débogage

        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUri)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.to)
                    .into(holder.imageView);
        } else {
            // S'assurer de ne pas réutiliser une ancienne image
            holder.imageView.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecetteDetailActivity.class);
            intent.putExtra("recette_id", recette.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recetteList != null ? recetteList.size() : 0;
    }

    public static class RecetteViewHolder extends RecyclerView.ViewHolder {
        public TextView titreTextView;
        public TextView categorieTextView;
        public ImageView imageView;

        public RecetteViewHolder(View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.titreRecette);
            categorieTextView = itemView.findViewById(R.id.categorieRecette);
            imageView = itemView.findViewById(R.id.imageRecette);
        }
    }
}
