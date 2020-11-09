package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import models.User;

public class SignUp extends AppCompatActivity {
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        createAccountButton = findViewById(R.id.create_account);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean trigger = false;
                for (User user: User.getUsers()) {
                    if (user.getEmail().equals(email.getText().toString())) {
                        email.setError("Email Already Taken");
                        trigger = true;
                    }
                }
                if (firstName.length() == 0) {
                    firstName.setError("Enter First Name");
                } else if (lastName.length() == 0) {
                    lastName.setError("Enter Last Name");
                } else if (email.length() == 0) {
                    email.setError("Enter Email");
                } else if (password.length() == 0) {
                    password.setError("Enter a Password");
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError("Password Does Not Match");
                } else if (!trigger) {
                    // Create models.User
                    User user = new User(firstName.getText().toString(), lastName.getText().toString(),
                            email.getText().toString(), password.getText().toString());
                    MainActivity.setCurrentUser(user);

                    // Navigate to next screen
                    nextScreen(Inventory.class);
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
