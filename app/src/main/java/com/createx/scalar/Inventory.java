package com.createx.scalar;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {
    private Button toLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ArrayList<String> items = new ArrayList<>();
        items.add("MILK\n79% Remaining | 4 days old");
        items.add("BUTTER\n90% Remaining | 2 days old");
        items.add("ORANGE JUICE\n55% Remaining | 5 days old");
        items.add("EGG\n95% Remaining | 1 days old");
        ListView inventoryList = findViewById(R.id.inventoryList);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        inventoryList.setAdapter(arrayAdapter);

//        toLogoutButton = findViewById(R.id.to_logout);
//        toLogoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nextScreen(LogOut.class);
//            }
//        });
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
