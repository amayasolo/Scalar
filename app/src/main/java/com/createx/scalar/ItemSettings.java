package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;

public class ItemSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_settings);


        // Retrieving Item that was clicked Info
        EditText itemName = findViewById(R.id.item_name);
        EditText itemSensorID = findViewById(R.id.item_sensor_id);
        EditText itemRemain = findViewById(R.id.item_remain);

        String name = Inventory.getItemClicked().getName();
        itemName.setText(String.valueOf(name));

        String sensorID = Inventory.getItemClicked().getID();
        itemSensorID.setText(String.valueOf(sensorID));

        double remain = Inventory.getItemClicked().getPercentage();
        itemRemain.setText(String.valueOf(remain));

        // Back to Inventory
        AppCompatImageButton itemToInventory = findViewById(R.id.item_to_inv);
        itemToInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen(Inventory.class);
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
