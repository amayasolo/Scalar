package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;

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
                } else {
                    // Create User


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
