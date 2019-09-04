package com.leidos.github.exampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnotherActivity extends AppCompatActivity {

    private ToHomeFragment mToHomeFragment = null;
    Button toFragmentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        mToHomeFragment = new ToHomeFragment();
        toFragmentButton = (Button) findViewById(R.id.buttonToFragment);
        toFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_view, mToHomeFragment, "ToHomeFragment").commit();
            }
        });
    }
}
