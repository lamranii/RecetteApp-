package com.example.library.UI.Reset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Data.Db.AppDatabase;
import com.example.library.Data.model.User;
import com.example.library.UI.home.MainActivity;
import com.example.library.R;

public class ResetPassActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button sendResetLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        usernameEditText = findViewById(R.id.usernameEditText);
        sendResetLinkButton = findViewById(R.id.sendResetLinkButton);

        sendResetLinkButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            if (!username.isEmpty()) {

                new Thread(() -> {
                    // Use getInstance instead of getDatabase
                    User user = AppDatabase.getInstance(this).userDao().getUserByUsername(username);

                    runOnUiThread(() -> {
                        if (user != null) {
                            // Logic to send the email
                            String email = user.getEmail();
                            if (email != null && !email.isEmpty()) {
                                sendResetLink(email);
                            } else {
                                Toast.makeText(this, "Aucun email associé à cet utilisateur", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            } else {
                Toast.makeText(this, "Veuillez entrer un nom d'utilisateur", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResetLink(String email) {
        // Create a mock reset link (you can customize this or generate with a real URL)
        String resetLink = "https://example.com/reset-password?email=" + email;

        // Create an intent to send the email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Réinitialisation de mot de passe");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Cliquez sur ce lien pour réinitialiser votre mot de passe: " + resetLink);

        try {
            startActivity(Intent.createChooser(emailIntent, "Envoyer un e-mail via..."));
            Toast.makeText(this, "Lien de réinitialisation envoyé!", Toast.LENGTH_SHORT).show();

            Intent loginIntent = new Intent(ResetPassActivity.this, MainActivity.class);
            startActivity(loginIntent);
            finish();  // Close the current activity
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Aucun client de messagerie trouvé", Toast.LENGTH_SHORT).show();
        }
    }
}
