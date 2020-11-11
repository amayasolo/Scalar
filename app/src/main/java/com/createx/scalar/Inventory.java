package com.createx.scalar;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ArrayList<String> items = new ArrayList<>();
        items.add("MILK");
        items.add("BUTTER");
        items.add("ORANGE JUICE");
        items.add("EGG");
        ListView inventoryList = (ListView) findViewById(R.id.inventoryList);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.activity_list_item, items);
        inventoryList.setAdapter(arrayAdapter);
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
