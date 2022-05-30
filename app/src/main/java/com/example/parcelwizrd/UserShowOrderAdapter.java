package com.example.parcelwizrd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserShowOrderAdapter extends RecyclerView.Adapter<UserShowOrderAdapter.MyViewHolder> {

    Context mContext;
    List<UserOrderModel> mList= new ArrayList<>();

    public UserShowOrderAdapter(Context mContext, ArrayList<UserOrderModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserOrderModel orderModel = mList.get(position);
        holder.DeliveryNumber.setText(orderModel.getDeliveryNumber());
        holder.DeliveryName.setText(orderModel.getDeliveryFirstName());
        holder.DeliveryLocation.setText(orderModel.getDropOff());
        holder.PickUpName.setText(orderModel.getPickUp());
        holder.PickUpNumber.setText(orderModel.getPickUpNumber());
        holder.PickUpLocation.setText(orderModel.getPickUp());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  PickUpLocation, DeliveryLocation, PickUpName, DeliveryName, PickUpNumber, DeliveryNumber;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            PickUpLocation= itemView.findViewById(R.id.tv_pickup_location);
            DeliveryLocation = itemView.findViewById(R.id.tv_delivery_location);
            PickUpName = itemView.findViewById(R.id.tv_pickup_name);
            DeliveryName= itemView.findViewById(R.id.tv_delivery_name);
            PickUpNumber= itemView.findViewById(R.id.tv_pickup_number);
            DeliveryNumber= itemView.findViewById(R.id.tv_delivery_number);
        }
    }
}
