package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import models.User;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {
    private static ListView inventoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryList = findViewById(R.id.inventoryList);

        updateListView();

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
    }

    /**
     * Method that switches between activities
     * @param screen next screen
     */
    public void nextScreen(Class screen) {
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }

    public static void updateListView() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(Inventory.inventoryList.getContext(), android.R.layout.simple_list_item_1,
                MainActivity.getCurrentUser().getScaleDisplay());
        inventoryList.setAdapter(arrayAdapter);
    }
}
