package com.sphtech.ui.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphtech.R;
import com.sphtech.model.MobileData;

public class MobileDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private AdapterMobileData.ClickListener clickListener;
    private TextView tvTvQuart;
    private View viewDecrease;
    private TextView tvTvVOM;

    <T extends View> T findViewById(int id) {
        return itemView.findViewById(id);
    }

    public MobileDataHolder(View itemView) {
        super(itemView);
        viewDecrease = findViewById(R.id.img_image_decrease);
        tvTvQuart = findViewById(R.id.tv_value_quarter);
        tvTvVOM = findViewById(R.id.tv_value_vlofmobile);
    }


    public void bind(Context context, @NonNull MobileData.Record model, AdapterMobileData.ClickListener clickListener) {
        this.clickListener = clickListener;
        tvTvQuart.setText(String.valueOf(model.getYear()));
        tvTvVOM.setText(String.valueOf(model.getVolume_of_mobile_data()));
        viewDecrease.setVisibility(model.isDecrease()?View.VISIBLE:View.INVISIBLE);
    }


    @Override
    public void onClick(View view) {
        if (clickListener != null) {
            clickListener.onItemClicked(view, getAdapterPosition());
        }
    }


}
