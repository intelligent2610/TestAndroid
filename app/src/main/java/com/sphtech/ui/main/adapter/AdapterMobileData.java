package com.sphtech.ui.main.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sphtech.R;
import com.sphtech.model.MobileData;

import java.util.ArrayList;
import java.util.List;


public class AdapterMobileData extends RecyclerView.Adapter<MobileDataHolder>{

    private List<MobileData.Record> mListModels;
    private Context mContext;
    private ClickListener clickListener;

    public AdapterMobileData(Context context, List<MobileData.Record> initList, ClickListener clickListener) {
        this.mListModels = initList;
        if (this.mListModels == null) {
            this.mListModels = new ArrayList<>();
        }
        this.mContext = context;
        this.clickListener = clickListener;
    }

    @Override
    public MobileDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.item_mobile_data_layout;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MobileDataHolder(v);
    }

    @Override
    public void onBindViewHolder(MobileDataHolder holder, int position) {
        MobileData.Record model = mListModels.get(position);
        if (model != null) {
            holder.bind(mContext, model, clickListener);
        }
    }

    public void set(List<MobileData.Record> channels) {
        if(channels == null){
            channels = new ArrayList<>();
        }
        mListModels.clear();
        mListModels.addAll(channels);
    }


    @Override
    public int getItemCount() {
        return mListModels.size();
    }

    public MobileData.Record getItem(int position) {
        if (position < mListModels.size()) {
            return mListModels.get(position);
        }
        return null;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClicked(View v, int position);
    }
}
