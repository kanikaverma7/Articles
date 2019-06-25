package com.dexter.newsapp.ui.newsList;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dexter.newsapp.R;
import com.dexter.newsapp.data.model.Article;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private List<Article> articleList;
    private Context context;
    private boolean isOnline;
    private onClick onClick;

    private final RequestManager requestManager;


    NewsListAdapter(List<Article> articleList, Context context, boolean isOnline, NewsListAdapter.onClick onClick) {
        this.articleList = articleList;
        this.context = context;
        this.isOnline = isOnline;
        this.onClick = onClick;
        this.requestManager = Glide.with(context);

    }


    void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        ImageView imageIcon;
        CardView newsLayout;
        private final Typeface newsTextFont;


        MyViewHolder(final View view) {
            super(view);
            newsTextFont = Typeface.createFromAsset(context.getAssets(), "fonts/UniversLTStd-LightCn.otf");

            titleText = (TextView) view.findViewById(R.id.newsTitle);
            imageIcon = (ImageView) view.findViewById(R.id.newsImage);
            newsLayout = (CardView) view.findViewById(R.id.newsLayout);
            titleText.setTypeface(newsTextFont);
        }

    }

    @NonNull
    @Override
    public NewsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_element, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (articleList.get(i).getTitle() != null)
            myViewHolder.titleText.setText(articleList.get(i).getTitle());

        if (articleList.get(i).getImageUrl() != null) {
            Uri url = Uri.parse(articleList.get(i).getImageUrl());
            requestManager
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(myViewHolder.imageIcon);
        }

        myViewHolder.newsLayout.setOnClickListener(v -> {
            ActivityOptions options = null;
            Pair[] pair = new Pair[1];
            pair[0] = new Pair<View, String>(myViewHolder.imageIcon, "Image_shared");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);
            }

            onClick.onItemClick(i, options.toBundle());

        });

    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public interface onClick {
        void onItemClick(int position, Bundle bundle);
    }
}
