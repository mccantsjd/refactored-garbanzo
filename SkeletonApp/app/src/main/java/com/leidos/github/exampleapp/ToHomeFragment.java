package com.leidos.github.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ToHomeFragment extends Fragment {

    private View ToHomeView = null;

    public ToHomeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ToHomeView = inflater.inflate(R.layout.fragment_to_home, container, false);
        return ToHomeView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        BuildLayout();
    }

    private void BuildLayout() {
        Button toHomeButton = (Button) ToHomeView.findViewById(R.id.buttonToHome);
        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToHomeFragment.super.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
