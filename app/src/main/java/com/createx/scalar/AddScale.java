package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import models.User;

import java.util.ArrayList;

public class AddScale extends AppCompatActivity {
    private Button initializeWeight;
    private Button addToInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scale);

        initializeWeight = findViewById(R.id.initialize_weight);
        initializeWeight.setOnClickListener(new View.OnClickListener() {

            // LOGIC THAT VALIDATES THE INITIALIZATION
            @Override
            public void onClick(View v) {
                nextScreen(Inventory.class);
            }
        });

        addToInventory = findViewById(R.id.add_to_inv);
        addToInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen(Inventory.class);
            }
        });

    }
    // From initialize weight to Inventory Screen

    /**
     * Method that switches between activities
     * @param screen next screen
     */
    public void nextScreen(Class screen) {
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }
}
