package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import models.User;

public class Login extends AppCompatActivity {
    private Button loginButton;
    private TextInputEditText email;
    private TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);

        // To Inventory Screen
        loginButton = findViewById(R.id.login2);
        final Class createAccount = SignUp.class;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.length() == 0 || email == null) {
                    email.setError("Enter Valid Email");
                } else if (password.length() == 0 || password == null) {
                    password.setError("Enter Valid Password");
                } else {
                    for (User user : User.getUsers()) {
                        if (user.getEmail().equals(email.getText().toString())
                                && user.getPassword().equals(password.getText().toString())) {
                            // Found User
                            MainActivity.setCurrentUser(user);
                            nextScreen(Inventory.class);
                        } else {
                            email.setError("Email or Password Invalid");
                        }
                    }
                }
            }
        });
    }

    /**
     * Method that switches between activities
     * @param screen next screen
     */
    public void nextScreen(Class screen) {
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }
}
