package com.example.twoMountains.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twoMountains.R;
import com.example.twoMountains.bean.ArticleBean;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private OnItemClickListener onItemClickListener; // 保存点击事件监听器

    private List<ArticleBean> articleList;

    /*
    定义一个接口，用于回调点击事件
    */
    public interface OnItemClickListener {
        // 实现 onItemClick 方法来处理点击事件
        void onItemClick(ArticleBean article);
    }

    public ArticleAdapter(List<ArticleBean> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleBean article = articleList.get(position);
        holder.bind(article);
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNumber, txtTitle;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txt_number);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }

        public void bind(ArticleBean article) {
            txtNumber.setText(String.valueOf(article.id));
            txtTitle.setText(article.title);
        }
    }

    /*设置点击事件监听器*/
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
