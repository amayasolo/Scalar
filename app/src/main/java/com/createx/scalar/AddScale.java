package com.createx.scalar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AddScale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scale);
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
