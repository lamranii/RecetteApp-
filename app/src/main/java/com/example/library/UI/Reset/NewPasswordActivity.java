package com.example.library.UI.Reset;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Data.Db.AppDatabase;
import com.example.library.Data.model.User;
import com.example.library.R;

public class NewPasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText, emailOrPhoneEditText;
    private Button confirmResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        emailOrPhoneEditText = findViewById(R.id.emailEditText); // Champ pour email ou téléphone
        confirmResetButton = findViewById(R.id.confirmResetButton);

        confirmResetButton.setOnClickListener(view -> {
            String newPassword = newPasswordEditText.getText().toString();
            String emailOrPhone = emailOrPhoneEditText.getText().toString();

            if (!newPassword.isEmpty() && !emailOrPhone.isEmpty()) {

                User user = AppDatabase.getInstance(this).userDao().getUserByEmail(emailOrPhone);

                if (user != null) {

                    user.setPassword(newPassword);
                    AppDatabase.getInstance(this).userDao().insert(user);
                    Toast.makeText(this, "Mot de passe réinitialisé avec succès !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Veuillez entrer un email/téléphone et un nouveau mot de passe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
