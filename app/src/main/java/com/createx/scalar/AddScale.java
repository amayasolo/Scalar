package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import models.Scale;
import models.User;

import java.util.ArrayList;

public class AddScale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scale);

        // Inputs
        final TextInputEditText sensorID = findViewById(R.id.sensor_id);
        final TextInputEditText itemName = findViewById(R.id.item_name);

        Button initializeWeight = findViewById(R.id.initialize_weight);
        initializeWeight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // INITIALIZING SCALE
                User currentUser = MainActivity.getCurrentUser();
                Scale scale = new Scale(sensorID.getText().toString(), itemName.getText().toString());
                currentUser.addScale(scale);
                Inventory.updateListView(scale, true);
                nextScreen(Inventory.class);
            }
        });

        ImageButton goToInventory = findViewById(R.id.add_to_inv);
        goToInventory.setOnClickListener(new View.OnClickListener() {
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
