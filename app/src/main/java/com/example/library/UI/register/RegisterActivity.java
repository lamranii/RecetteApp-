package com.example.library.UI.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Data.Db.AppDatabase;
import com.example.library.Data.model.User;
import com.example.library.UI.home.MainActivity;
import com.example.library.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialisation des vues
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.EmailEditText);
        phoneEditText = findViewById(R.id.PhoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.btnBack); // Initialisation du bouton de retour

        // Configuration du bouton de retour
        backButton.setOnClickListener(v -> {
            // Animation de retour
            v.animate()
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f);
                        navigateToMainActivity();
                    });
        });

        registerButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                User user = new User(username, password, email, phone);

                new Thread(() -> {
                    AppDatabase.getInstance(RegisterActivity.this).userDao().insert(user);
                    Log.d("RegisterActivity", "Utilisateur inséré : " + user.getUsername());

                    User insertedUser = AppDatabase.getInstance(RegisterActivity.this).userDao().getUserByCredentials(username, password);

                    runOnUiThread(() -> {
                        if (insertedUser != null) {
                            Toast.makeText(this, "Utilisateur ajouté avec succès : " + insertedUser.getUsername(), Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(this::navigateToMainActivity, 1000);
                        } else {
                            Toast.makeText(this, "Erreur d'ajout de l'utilisateur", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            } else {
                Toast.makeText(this, "Merci de remplir tous les champs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMainActivity();
    }
}