package com.example.challengefragmentrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CarAdapter.ItemClicked {
    Button btnCarInfo, btnOwnerInfo;
    ImageView ivMake;
    TextView tvModel, tvName, tvTel;
    FragmentManager fragmentManager;
    Fragment buttonFrag, listFrag, carInfoFrag, ownerInfoFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();

        listFrag = fragmentManager.findFragmentById(R.id.listFrag);
        buttonFrag = fragmentManager.findFragmentById(R.id.buttonFrag);
        carInfoFrag = fragmentManager.findFragmentById(R.id.carInfoFrag);
        ownerInfoFrag = fragmentManager.findFragmentById(R.id.ownerInfoFrag);

        fragmentManager.beginTransaction()
                .show(buttonFrag)
                .show(listFrag)
                .show(carInfoFrag)
                .show(ownerInfoFrag)
                .commit();

    }

    @Override
    public void onItemClicked(int index) {
        tvName.setText(ApplicationClass.cars.get(index).getOwnerName());
        tvModel.setText(ApplicationClass.cars.get(index).getModel());
        tvTel.setText(ApplicationClass.cars.get(index).getPhoneNumber());

        if (ApplicationClass.cars.get(index).getMake().equals("Volkswagen")) {
            ivMake.setImageResource(R.drawable.vw);
        } else if (ApplicationClass.cars.get(index).getMake().equals("nissan")) {
            ivMake.setImageResource(R.drawable.nissan);
        } else {
            ivMake.setImageResource(R.drawable.mercedes);
        }

    }
}