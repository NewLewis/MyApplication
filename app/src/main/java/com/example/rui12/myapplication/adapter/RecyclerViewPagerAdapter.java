package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class RecyclerViewPagerAdapter extends RecyclerView.Adapter<RecyclerViewPagerAdapter.NIHolder>{
    private LayoutInflater mInflater;
    private List<DreamModel> dreamModelList;
    private OnItemClickListener mOnItemClickListener = null;

    public RecyclerViewPagerAdapter(Context context, List<DreamModel> dreamModelList, int showStyle) {
        super();
        this.dreamModelList = dreamModelList;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return dreamModelList.size();
    }

    @NonNull
    @Override
    public NIHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        final NIHolder niHolder = new NIHolder(mInflater.inflate(R.layout.content_cardview, parent, false));
        //为item设置点击事件
        niHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 绑定了点击事件");
                mOnItemClickListener.onItemClick(niHolder.getAdapterPosition());
            }
        });

        niHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: 绑定了长按事件");
                mOnItemClickListener.onItemLongClick(niHolder.getAdapterPosition());
                return false;
            }
        });
        return niHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewPagerAdapter.NIHolder holder, int position) {
        //
        holder.nineGridImageView.setImagesData(dreamModelList.get(position).getPhotoModelList());
        holder.user_name.setText(dreamModelList.get(position).getUser_name());

        Log.d("jaeger", "九宫格高度: " + holder.nineGridImageView.getMeasuredHeight());
        Log.d("jaeger", "item 高度: " + holder.itemView.getMeasuredHeight());
    }


    public void remove(int position){
        dreamModelList.remove(position);
        notifyItemRemoved(position);
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
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
                Log.d(TAG, "onItemImageLongClick: 长按图片item");
                return super.onItemImageLongClick(context, imageView, index, list);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<PhotoModel> list) {
                Log.d(TAG, "onItemImageClick: 点击图片item");
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