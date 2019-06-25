package com.dexter.newsapp.ui.detailNews;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dexter.newsapp.R;
import com.dexter.newsapp.data.model.Article;
import com.dexter.newsapp.ui.newsList.NewsListActivity;

import java.io.File;

public class DetailedNews extends AppCompatActivity {

    int position;
    Article article;
    ImageView newsImage;
    TextView authorText;
    TextView titleText;
    TextView contentText;
    TextView descriptionText;
    TextView urlText;
    TextView publishedAtText;

    TextView authorTextHeading;
    TextView titleTextHeading;
    TextView contentTextHeading;
    TextView descriptionTextHeading;
    TextView urlTextHeading;
    TextView publishedAtTextHeading;

    private Typeface valueFonts;
    private Typeface headingFonts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);


        position = getIntent().getIntExtra("position", -1);
        article = NewsListActivity.articleList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(article.getAuthor());

            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }


        newsImage = (ImageView) findViewById(R.id.newsImage);
        authorText = (TextView) findViewById(R.id.authorvalue);
        titleText = (TextView) findViewById(R.id.titleTextValue);
        descriptionText = (TextView) findViewById(R.id.descriptionTextvalue);
        contentText = (TextView) findViewById(R.id.contentTextvalue);
        urlText = (TextView) findViewById(R.id.urlTextvalue);
        publishedAtText = (TextView) findViewById(R.id.publishedValue);

        authorTextHeading = (TextView) findViewById(R.id.authorTextHeading);
        titleTextHeading = (TextView) findViewById(R.id.titleTextHeading);
        descriptionTextHeading = (TextView) findViewById(R.id.descriptionTextHeading);
        contentTextHeading = (TextView) findViewById(R.id.contentTextHeading);
        urlTextHeading = (TextView) findViewById(R.id.urlTextHeading);
        publishedAtTextHeading = (TextView) findViewById(R.id.publishedText);

        valueFonts = Typeface.createFromAsset(getAssets(), "fonts/UniversLTStd-LightCn.otf");
        headingFonts = Typeface.createFromAsset(getAssets(), "fonts/UniversCondensedBold.ttf");
        urlText.setPaintFlags(urlText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlText != null && !urlText.equals("")) {
                    Uri uri = Uri.parse(urlText.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });


        bindvalues();
        settingTypeFace();

    }

    private void settingTypeFace() {


        authorText.setTypeface(valueFonts);
        titleText.setTypeface(valueFonts);
        descriptionText.setTypeface(valueFonts);
        contentText.setTypeface(valueFonts);
        urlText.setTypeface(valueFonts);
        publishedAtText.setTypeface(valueFonts);

        authorTextHeading.setTypeface(headingFonts);
        titleTextHeading.setTypeface(headingFonts);
        descriptionTextHeading.setTypeface(headingFonts);
        contentTextHeading.setTypeface(headingFonts);
        urlTextHeading.setTypeface(headingFonts);
        publishedAtTextHeading.setTypeface(headingFonts);


    }

    public void bindvalues() {
        if (article.getImageUrl() != null) {
            Glide.with(this)
                    .load(article.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(newsImage);
        }

        if (article.getAuthor() != null && !article.getAuthor().equals("")) {
            authorText.setText(article.getAuthor());
        } else {
            authorText.setText("N/A");
        }
        if (article.getTitle() != null && !article.getTitle().equals("")) {
            titleText.setText(article.getTitle());
        } else {
            titleText.setText("N/A");
        }
        if (article.getDescription() != null && !article.getDescription().equals("")) {
            descriptionText.setText(article.getDescription());
        } else {
            descriptionText.setText("N/A");
        }
        if (article.getContent() != null && !article.getContent().equals("")) {
            contentText.setText(article.getContent());
        } else {
            contentText.setText("N/A");
        }
        if (article.getUrl() != null && !article.getUrl().equals("")) {

            urlText.setText(article.getUrl());
        } else {
            urlText.setText("N/A");
        }
        if (article.getPublishedAt() != null && !article.getPublishedAt().equals("")) {
            publishedAtText.setText(article.getPublishedAt());
        } else {
            publishedAtText.setText("N/A");
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }
}
