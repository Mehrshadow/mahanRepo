package ir.aspacrm.my.app.mahan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahan.adapter.AdapterSingleNews;
import ir.aspacrm.my.app.mahan.model.News;

public class ActivityShowSingleNews extends AppCompatActivity {
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.lstNews)
    RecyclerView lstNews;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    AdapterSingleNews adapterNews;
    LinearLayoutManager linearLayoutManager;
    List<News> newses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        G.currentActivity = this;
        G.context = this;

        ButterKnife.bind(G.currentActivity);

        Intent i = getIntent();

        News news = new News();
        news.title = i.getStringExtra("title");
        news.bodyText = i.getStringExtra("body");
        news.newsDate = i.getStringExtra("date");

        newses.add(news);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNews = new AdapterSingleNews(newses);
        linearLayoutManager = new LinearLayoutManager(G.currentActivity);
        lstNews.setLayoutManager(linearLayoutManager);
        lstNews.setAdapter(adapterNews);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
