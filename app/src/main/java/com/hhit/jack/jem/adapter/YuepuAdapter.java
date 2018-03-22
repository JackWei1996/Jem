package com.hhit.jack.jem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhit.jack.jem.R;
import com.hhit.jack.jem.YuepuActivity;
import com.hhit.jack.jem.entity.Yuepu;


import java.util.List;

/**
 * Created by 19604 on 3/17/2018.
 */

public class YuepuAdapter extends RecyclerView.Adapter<YuepuAdapter.ViewHolder> {
    private Context mContext;
    private List<Yuepu> yuepuList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView yuepuImage;
        TextView yuepuName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            yuepuImage = (ImageView) itemView.findViewById(R.id.yuepu_image);
            yuepuName = (TextView) itemView.findViewById(R.id.yuepu_name);
        }
    }

    public YuepuAdapter(List<Yuepu> yuepuList) {
        this.yuepuList = yuepuList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.yuepu_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Yuepu yuepu = yuepuList.get(position);
                Intent intent = new Intent(mContext,YuepuActivity.class);
                intent.putExtra(YuepuActivity.FRUIT_NAME, yuepu.getName());
                intent.putExtra(YuepuActivity.FRUIT_IMAGE_ID, yuepu.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Yuepu yuepu = yuepuList.get(position);
        holder.yuepuName.setText(yuepu.getName());
        Glide.with(mContext).load(yuepu.getImageId()).into(holder.yuepuImage);
    }

    @Override
    public int getItemCount() {
        return yuepuList.size();
    }
}
