package com.example.challengefragmentrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private ArrayList<Car> cars;

    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }

    public CarAdapter (Context context, ArrayList<Car> list)
    {
        cars = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMake;
        TextView tvModel, tvOwner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMake = itemView.findViewById(R.id.ivMake);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvOwner = itemView.findViewById(R.id.tvOwner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(cars.indexOf((Car) view.getTag()));
                }
            });

        }
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(cars.get(i));
        viewHolder.tvOwner.setText(cars.get(i).getOwnerName());
        viewHolder.tvModel.setText(cars.get(i).getModel());
        if (cars.get(i).getMake().equals("Volkswagen")){
            viewHolder.ivMake.setImageResource(R.drawable.vw);
        } else if (cars.get(i).getMake().equals("Nissan")) {
            viewHolder.ivMake.setImageResource(R.drawable.nissan);
        } else {
            viewHolder.ivMake.setImageResource(R.drawable.mercedes);
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
