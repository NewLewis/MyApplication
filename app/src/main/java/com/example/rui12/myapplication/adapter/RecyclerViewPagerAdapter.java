package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.model.PhotoModel;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.ItemImageLongClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPagerAdapter extends RecyclerView.Adapter<RecyclerViewPagerAdapter.NIHolder>{
    private LayoutInflater mInflater;
    private List<DreamModel> dreamModelList;

    public RecyclerViewPagerAdapter(Context context, List<DreamModel> dreamModelList, int showStyle) {
        super();
        this.dreamModelList = dreamModelList;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return dreamModelList.size();
    }

    @Override
    public NIHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NIHolder(mInflater.inflate(R.layout.content_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewPagerAdapter.NIHolder holder, int position) {
        //
        holder.nineGridImageView.setImagesData(dreamModelList.get(position).getPhotoModelList());
        holder.user_name.setText(dreamModelList.get(position).getUser_name());

        Log.d("jaeger", "九宫格高度: " + holder.nineGridImageView.getMeasuredHeight());
        Log.d("jaeger", "item 高度: " + holder.itemView.getMeasuredHeight());
    }


    public void Remove(int position){
        dreamModelList.remove(position);
        notifyItemRemoved(position);
    }

    class NIHolder extends RecyclerView.ViewHolder {
        private NineGridImageView<PhotoModel> nineGridImageView;
        private TextView user_name;

        private NineGridImageViewAdapter<PhotoModel> mAdapter = new NineGridImageViewAdapter<PhotoModel>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, PhotoModel photoModel) {
                Picasso
                        .with(context)
                        .load(photoModel.getUrl())
                        .placeholder(photoModel.getLocalRes())
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
                return super.onItemImageLongClick(context, imageView, index, list);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
                super.onItemImageClick(context, imageView, index, list);
            }
        };

        NIHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            nineGridImageView = itemView.findViewById(R.id.nine_image);
            nineGridImageView.setAdapter(mAdapter);
            nineGridImageView.setItemImageClickListener(new ItemImageClickListener<PhotoModel>() {
                @Override
                public void onItemImageClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {

                }
            });
            nineGridImageView.setItemImageLongClickListener(new ItemImageLongClickListener<PhotoModel>() {
                @Override
                public boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
                    return false;
                }
            });
        }
    }
}