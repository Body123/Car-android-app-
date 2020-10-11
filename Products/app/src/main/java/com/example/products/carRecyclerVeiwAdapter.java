package com.example.products;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class carRecyclerVeiwAdapter extends RecyclerView.Adapter<carRecyclerVeiwAdapter.carViewHolder> {
    public ArrayList<car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<car> cars) {
        this.cars = cars;
    }

    private ArrayList<car> cars;
    private onRecycleViewClickListener listener;
    public  carRecyclerVeiwAdapter(ArrayList<car> cars,onRecycleViewClickListener listener){
        this.cars=cars;
        this.listener=listener;
    }
    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout,null,false);
        carViewHolder viewHolder =new carViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull carViewHolder holder, int position) {
        car c=cars.get(position);
        if(c.getImage()!=null&&!c.getImage().isEmpty())
            holder.iv.setImageURI(Uri.parse(c.getImage()));
        else{
            holder.iv.setImageResource(R.drawable.city);
        }
        if(c.getModel()!=null&&!c.getModel().isEmpty())
            holder.txtModel.setText(c.getModel());
        if(c.getColor()!=null&&!c.getColor().isEmpty())
            holder.txtColor.setText(c.getColor());
        holder.txtDensity.setText(String.valueOf(c.getDistancePerLetter()));

        holder.iv.setTag(c.getId());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class carViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView txtModel,txtColor,txtDensity;
        public carViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.customCarImageView);
            txtModel=itemView.findViewById(R.id.txtModel);
            txtColor=itemView.findViewById(R.id.txtColor);
            txtDensity=itemView.findViewById(R.id.txtDensity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id= (int) iv.getTag();
                    listener.OnItemClick(id);
                }
            });
        }
    }
}
