package com.example.library.UI.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.library.Data.Db.AppDatabase;
import com.example.library.Data.model.User;
import com.example.library.R;
import com.example.library.UI.Reset.ResetPassActivity;
import com.example.library.UI.register.RegisterActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView signUpTextView;

    private AppDatabase db;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        signUpTextView = findViewById(R.id.signUpTextView);


        db = AppDatabase.getInstance(getApplicationContext());

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                executorService.execute(() -> {

                    User user = db.userDao().getUserByCredentials(username, password);

                    runOnUiThread(() -> {
                        if (user != null) {

                            Toast.makeText(MainActivity.this, "Welcome, " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(MainActivity.this, home.class);


                            Log.d("Login", "Intent launched to HomeActivity");


                            startActivity(intent);


                            Log.d("Login", "Calling finish()");


                            finish();
                        } else {

                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {

                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });



        forgotPasswordTextView.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, ResetPassActivity.class);
            startActivity(intent);
        });

        signUpTextView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}
