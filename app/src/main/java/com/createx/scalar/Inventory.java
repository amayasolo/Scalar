package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import models.Scale;
import models.User;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {
    private static ListView inventoryList;
    private static int positionCounter;
    private static Scale itemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryList = findViewById(R.id.inventoryList);

        ArrayAdapter arrayAdapter = new ArrayAdapter(Inventory.inventoryList.getContext(), android.R.layout.simple_list_item_1,
                MainActivity.getCurrentUser().getScaleDisplay());
        inventoryList.setAdapter(arrayAdapter);

        AppCompatImageButton toLogoutButton = findViewById(R.id.to_logout);
        toLogoutButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
              nextScreen(LogOut.class);     }
       });

        Button toAddScale = findViewById(R.id.to_add_scale);
        toAddScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen(AddScale.class);     }
        });

        // Handling Click Events in ListView
        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Scale curr: MainActivity.getCurrentUser().getScales()) {
                    if (curr.getPosition() == position) {
                        itemClicked = curr;
                    }
                }

                nextScreen(ItemSettings.class);
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

    public static void updateListView(Scale scale, boolean addition) {
        // Setting position of Scale
        if (addition) {
            scale.setPosition(positionCounter++);
        } else { // else rearrange all positions past deleted scale
            for (int i = scale.getPosition(); i < inventoryList.getCount(); i++) {
                for (Scale curr: MainActivity.getCurrentUser().getScales()) {
                    if (curr.getPosition() == i) {
                        curr.setPosition(curr.getPosition() - 1);
                    }
                }
            }
            positionCounter--;
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(Inventory.inventoryList.getContext(), android.R.layout.simple_list_item_1,
                MainActivity.getCurrentUser().getScaleDisplay());
        inventoryList.setAdapter(arrayAdapter);
    }

    /**
     * For item settings class to reference
     * @return the scale that was clicked
     */
    public static Scale getItemClicked() {
        return itemClicked;
    }
}
