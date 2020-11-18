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
    private static int positionCounter = -1;
    private static Scale itemClicked;
    private static ArrayAdapter arrayAdapter;
    private static boolean trigger = false; // to ensure array adapter isn't created again
    private static ArrayList<String> scaleDisplay = MainActivity.getCurrentUser().getScaleDisplay();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryList = findViewById(R.id.inventoryList);

        if (!trigger) {
            arrayAdapter = new ArrayAdapter(Inventory.inventoryList.getContext(), android.R.layout.simple_list_item_1,
                    scaleDisplay);
        }

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

        AppCompatImageButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inventory.this, "Refreshing Scales", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                itemClicked.setCurrentWeight(itemClicked.getCurrentWeight() * 0.645);
                updateItem();

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

    public static void updateListView(Scale scale, int mode) {
        trigger = true;
        // Setting position of Scale
        if (mode == 0) {
            scale.setPosition(positionCounter + 1);
            positionCounter++;
            arrayAdapter.add(scale.toString());
        } else if (mode == 1) { // else rearrange all positions past deleted scale
            for (int i = scale.getPosition(); i < inventoryList.getCount(); i++) {
                for (Scale curr: MainActivity.getCurrentUser().getScales()) {
                    if (curr.getPosition() == i) {
                        curr.setPosition(curr.getPosition() - 1);
                    }
                }
            }
            positionCounter--;
            arrayAdapter.remove(scale.toString());
        }
        arrayAdapter.notifyDataSetChanged();
    }

    public static void updateItem() {
        scaleDisplay.set(itemClicked.getPosition(), itemClicked.toString());
        arrayAdapter.notifyDataSetChanged();
    }


    /**
     * For item settings class to reference
     * @return the scale that was clicked
     */
    public static Scale getItemClicked() {
        return itemClicked;
    }

}
