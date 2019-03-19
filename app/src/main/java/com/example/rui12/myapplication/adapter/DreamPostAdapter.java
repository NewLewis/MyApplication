package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.DreamModel;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class DreamPostAdapter extends RecyclerView.Adapter<DreamPostAdapter.NIHolder>{
    private LayoutInflater mInflater;
    private List<DreamModel> dreamModelList;
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;

    public DreamPostAdapter(Context context, List<DreamModel> dreamModelList, int showStyle) {
        super();
        this.dreamModelList = dreamModelList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getItemCount() {
        return dreamModelList.size();
    }

    @NonNull
    @Override
    public NIHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        final NIHolder niHolder = new NIHolder(mInflater.inflate(R.layout.item_dream_post, parent, false));
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
    public void onBindViewHolder(@NonNull final DreamPostAdapter.NIHolder holder, int position) {
        holder.user_name.setText(dreamModelList.get(position).getUser_name());
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
        private TextView user_name;

        NIHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
        }
    }
}