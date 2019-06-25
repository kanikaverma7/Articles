package com.dexter.newsapp.ui.newsList;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.dexter.newsapp.App;
import com.dexter.newsapp.R;
import com.dexter.newsapp.data.model.Article;
import com.dexter.newsapp.ui.detailNews.DetailedNews;
import com.dexter.newsapp.utils.ApiResponse;
import com.dexter.newsapp.utils.ViewModelFactory;
import com.dexter.newsapp.viewModel.NewsListViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements NewsListAdapter.onClick {


    @Inject
    ViewModelFactory viewModelFactory;

    NewsListViewModel viewModel;
    public static List<Article> articleList = new ArrayList<>();

    String country = "";
    String category = "";
    boolean isOnline = false;
    boolean isLoading = false;
    NewsListAdapter listAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView newsFeed;
    Typeface valueFonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getIntent().getStringExtra("OnlineStatus").equalsIgnoreCase("Online")) {
            isOnline = true;
        }
        newsFeed = (TextView) findViewById(R.id.newsFeed);
        newsFeed.setPaintFlags(newsFeed.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        valueFonts = Typeface.createFromAsset(getAssets(), "fonts/UniversLTStd-LightCn.otf");
        newsFeed.setTypeface(valueFonts);
        listAdapter = new NewsListAdapter(articleList, NewsListActivity.this, isOnline, NewsListActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setHasFixedSize(true);
        initScrollListener();

        ((App) getApplicationContext()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);
        viewModel.fetchApiResponse().observe((LifecycleOwner) this, this::consumeApiResponse);
        viewModel.fetchDbResponse().observe((LifecycleOwner) this, this::consumeDbResponse);

        progressDialog = new ProgressDialog(this, R.style.ProgressTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        country = getIntent().getStringExtra("Country");
        category = getIntent().getStringExtra("Category");


        if (isOnline)
            viewModel.fetchDataFromApi(country, category, 1);
        else
            viewModel.fetchDataFromDb(country, category);
        articleList.clear();
    }

    private void initScrollListener() {

        final int[] currentPage = {1};
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    final int visibleThreshold = 2;

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = linearLayoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + visibleThreshold && isOnline == true && isLoading == false) {
                        isLoading = true;
                        currentPage[0]++;
                        progressDialog.show();
                        viewModel.fetchDataFromApi(country, category, currentPage[0]);
                    }
                }
            }
        });


    }

    private void consumeDbResponse(Article article) {
        if (article == null) {
            // do nothing
        }
    }

    private void consumeApiResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:

                progressDialog.dismiss();

                isLoading = false;
                articleList.addAll(apiResponse.data.getArticleList());
                if (!isOnline) {
                    if (articleList.size() == 0) {
                        Toast.makeText(this, "No saved list found.Please check for internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        listAdapter.setArticleList(articleList);
                        listAdapter.notifyDataSetChanged();
                    }

                } else {
                    listAdapter.setArticleList(articleList);
                    listAdapter.notifyDataSetChanged();
                }


                if (isOnline)
                    for (Article article : apiResponse.data.getArticleList()) {
                        viewModel.updateInDb(article, country, category);
                    }

                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(NewsListActivity.this, "Something went wrong, Please try again.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position, Bundle bundle) {

        Intent i = new Intent(getApplicationContext(), DetailedNews.class);
        i.putExtra("position", position);
        startActivity(i, bundle);


    }


}
