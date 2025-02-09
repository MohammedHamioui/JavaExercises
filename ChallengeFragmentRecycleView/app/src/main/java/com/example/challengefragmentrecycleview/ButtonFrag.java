package com.example.challengefragmentrecycleview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ButtonFrag extends Fragment {
    Button btnCarInfo, btnOwnerInfo;

    public ButtonFrag() {
        super(R.layout.fragment_button); // Ensure this is the correct layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCarInfo = view.findViewById(R.id.btnCarInfo);
        btnOwnerInfo = view.findViewById(R.id.btnOwnerInfo);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment carInfoFrag = fragmentManager.findFragmentById(R.id.carInfoFrag);
        Fragment ownerInfoFrag = fragmentManager.findFragmentById(R.id.ownerInfoFrag);

        btnCarInfo.setOnClickListener(v -> {
            fragmentManager.beginTransaction()
                    .show(carInfoFrag)
                    .hide(ownerInfoFrag)
                    .commit();
        });

        btnOwnerInfo.setOnClickListener(v -> {
            fragmentManager.beginTransaction()
                    .hide(carInfoFrag)
                    .show(ownerInfoFrag)
                    .commit();
        });

    }
}
